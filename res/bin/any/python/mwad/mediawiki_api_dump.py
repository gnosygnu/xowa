#!/usr/bin/env python3
#-*-coding:utf-8-*-

'''mediawiki_api_dump.py: Creates a xml dump of all pages from the given wiki-url'''

__author__      = 'https://github.com/Mattze96'
__copyright__   = 'Copyright 2016, Planet Earth'
__version__ = '0.0.2'

import argparse
import logging
import urllib.parse, urllib.request
import json
import re
import time
import sys
import bz2

parser = argparse.ArgumentParser(
    description = 'Create a wiki xml-dump via api.php'
)

parser.add_argument('-v', '--verbose', action='count', default=0, help='verbose level... repeat up to three times\n')
parser.add_argument('-n', '--name', help='name of the wiki for filename etc.\n')
parser.add_argument('-l', '--log', help='specify log-file.\n')
parser.add_argument('-c', '--compress', action='store_true', help='compress output file with bz2\n')
parser.add_argument('-x', '--xowa', action='store_true', help='special XOWA mode: xml to stdout, progress to stderr')
parser.add_argument('wiki_url', metavar='url', help='download url\n') #nargs='+',

args = parser.parse_args()

logFormatter = logging.Formatter('%(asctime)s - %(message)s')
rootLogger = logging.getLogger()
rootLogger.setLevel(logging.INFO)

if args.log:
    fileHandler = logging.FileHandler(args.log)
    fileHandler.setFormatter(logFormatter)
    rootLogger.addHandler(fileHandler)

if args.xowa:
    args.verbose = 0

consoleHandler = logging.StreamHandler()
consoleHandler.setLevel(max(3 - args.verbose, 0) * 10)
consoleHandler.setFormatter(logFormatter)
rootLogger.addHandler(consoleHandler)

logging.info('Arguments: %s', str(vars(args)))

class ProgressBar(object):
    def __init__(self, total, width=40, symbol='#', output=sys.stderr):
        assert len(symbol) == 1

        self.total = total
        self.width = width
        self.symbol = symbol
        self.output = output
        self.fmt = re.sub(r'(?P<name>%\(.+?\))d', r'\g<name>%dd' % len(str(total)),
            '%(bar)s %(current)d/%(total)d (%(percent)3d%%)')

        self.current = 0

    def __call__(self):
        percent = self.current / float(self.total)
        size = int(self.width * percent)
        remaining = self.total - self.current
        bar = '[' + self.symbol * size + ' ' * (self.width - size) + ']'

        args = {
            'total': self.total,
            'bar': bar,
            'current': self.current,
            'percent': percent * 100,
            'remaining': remaining
        }
        print('\r' + self.fmt % args, file=self.output, end='')

    def done(self):
        self.current = self.total
        self()
        print('', file=self.output)

class Dumper():
    def __init__(self, wiki, api, compress, enable_progress, xowa_mode):
        self.wiki = wiki
        self.api = api
        self.compress = compress
        self.enable_progress = enable_progress
        self.writer = None
        self.pages_per_request = 50
        self.xowa = xowa_mode
        self.xowa_counter = 0
        self.xowa_max_counter = 0

    def xowa_status(self, *args, **kwargs):
        if not self.xowa:
            return
        print('[mwad]:', *args, file=sys.stderr, **kwargs)

    def start(self):
        self.xowa_status('getting statistics')
        statistics = self.get_statistics()
        self.xowa_status('got statistics')

        if self.enable_progress:
            print('Getting a list of all pages')
            self.progress = ProgressBar(statistics['pages'])

        self.xowa_status('getting namespaces')
        nss = self.get_nsids()
        self.xowa_status('got namespaces')
        self.xowa_status('getting pageids')
        self.xowa_counter = 0
        self.xowa_max_counter = statistics['pages']
        pageids = self.get_pageids(nss)
        self.xowa_status('got pageids')

        if self.enable_progress:
            print('Downloading pages...')
            self.progress = ProgressBar(len(pageids))

        self.xowa_status('starting xml-stream')
        self.xowa_counter = 0
        self.xowa_max_counter = len(pageids)
        self.merge_pages(pageids)
        self.xowa_status('stopped xml-stream')
        logging.info('Done')

    def get_nsids(self):
        nss = self.mw_siteinfo_namespaces()['query']['namespaces']
        #Why not negative ??
        return [x['id'] for x in nss.values() if x['id'] >= 0]

    def get_statistics(self):
        params = {
            'action': 'query',
            'meta': 'siteinfo',
            'siprop': 'statistics',
            'format': 'json',
        }
        return self.mw_api_json(params)['query']['statistics']

    def xml_writer(self, filename):
        if self.xowa:
            try:
                while True:
                    line = (yield)
                    #f.write(line.encode('utf-8'))
                    print(line, end='')
            except GeneratorExit:
                pass
            logging.info('XML-Stream: %s done.', filename)
        elif self.compress:
            with bz2.open(filename+'.bz2', 'w') as f:
                try:
                    while True:
                        line = (yield)
                        f.write(line.encode('utf-8'))
                except GeneratorExit:
                    pass
            logging.info('File: %s.bz2 done.', filename)
        else:
            with open(filename, 'w', encoding='utf-8') as f:
                try:
                    while True:
                        line = (yield)
                        f.write(line)
                except GeneratorExit:
                    pass
            logging.info('File: %s done.', filename)

    def merge_pages(self, pageids=[]):
        if not pageids:
            return

        self.writer = self.xml_writer('{0}-{1}-pages-articles.xml'.format(self.wiki, time.strftime('%Y%m%d')))
        next(self.writer)

        page = self.mw_export_pageids()

        self.writer.send(re.search('(<mediawiki.*>)', page).group(0))
        self.writer.send(re.search('(\s*?<siteinfo>.*?<\/siteinfo>)', page, re.DOTALL).group(0))

        for ids in self.__split_list(pageids, self.pages_per_request):
            logging.info('Current ids: %s', str(ids))
            page = self.mw_export_pageids(ids)
            sub_pages = 0
            for page in re.finditer('(\s*?<page>.*?<\/page>)', page, re.DOTALL):
                self.writer.send(page.group(0))
                sub_pages += 1

            if self.enable_progress:
                self.progress.current += sub_pages
                self.progress()
            self.xowa_counter += sub_pages
            self.xowa_status('getting pageids', '{}/{}'.format(self.xowa_counter, self.xowa_max_counter))


        self.writer.send('\n</mediawiki>\n')
        self.writer.close()

        if self.enable_progress:
            self.progress.done()
            self.progress = None

    def mw_export_pageids(self, pageids=[]):
        params = {
            'action': 'query',
            'pageids': '|'.join([str(x) for x in pageids]),
            'continue': '',
            'export': '',
            'exportnowrap': ''
        }
        return self.mw_api_text(params)

    def mw_api_text(self, params):
        data = urllib.parse.urlencode(params)
        response = urllib.request.urlopen('{}?{}'.format(self.api, data))
        logging.info('API: %s', response.geturl())
        return response.read().decode('utf-8')

    def mw_api_json(self, params):
        data = urllib.parse.urlencode(params)
        response = urllib.request.urlopen('{}?{}'.format(self.api, data))
        logging.info('API: %s', response.geturl())
        return json.loads(response.read().decode('utf-8'))

    def mw_list_allpages(self, apfrom=None, ns=0):
        params = {
            'action': 'query',
            'list': 'allpages',
            'aplimit': 500,
            'continue': '',
            'format': 'json',
            'apnamespace': ns
        }
        if apfrom:
            params.update({
                'apfrom': apfrom
            })
        return self.mw_api_json(params)


    def mw_siteinfo_namespaces(self):
        params = {
            'action': 'query',
            'meta': 'siteinfo',
            'siprop': 'namespaces',
            'format': 'json',
        }
        return self.mw_api_json(params)


    def get_pageids(self, nss=[0]):
        pageids = []
        for ns in nss:
            apfrom = None
            while True:
                result = self.mw_list_allpages(apfrom, ns)
                pageids.extend([x['pageid'] for x in result['query']['allpages']])
                if self.enable_progress:
                    self.progress.current += len(result['query']['allpages'])
                    self.progress()
                self.xowa_counter += len(result['query']['allpages'])
                self.xowa_status('getting pageids', '{}/{}'.format(self.xowa_counter, self.xowa_max_counter))
                if 'continue' not in result:
                    break
                apfrom = result['continue']['apcontinue']
        pageids.sort()
        logging.info('PageIds: %s', str(pageids))

        if self.enable_progress:
            self.progress.done()
            self.progress = None

        return pageids

    def __split_list(self, l, n):
         arrs = []
         while len(l) > n:
             sl = l[:n]
             arrs.append(sl)
             l = l[n:]
         arrs.append(l)
         return arrs


if __name__ == '__main__':
    API_URL = urllib.parse.urljoin(args.wiki_url, 'api.php')
    WIKI_NAME = args.name or urllib.parse.urlparse(args.wiki_url).netloc
    COMPRESS = (args.compress and not args.xowa)
    ENABLE_PROGRESS = (args.verbose == 0 and not args.xowa)
    XOWA_MODE = args.xowa

    dumper = Dumper(WIKI_NAME, API_URL, COMPRESS, ENABLE_PROGRESS, XOWA_MODE)
    dumper.start()
