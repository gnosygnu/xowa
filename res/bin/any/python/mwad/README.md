# mwad - mediawiki api


This is a script for creating xml-dumps via the mediawiki api.
It is still in early development, feel free to a new [issue](https://github.com/Mattze96/mwad/issues).

Importing to @gnosygnu's offline wikipedia-reader [XOWA](https://github.com/gnosygnu/xowa) works fine..

## Installation
No additional packages are required at the moment :)

---


## Run
```
usage: mediawiki_api_dump.py [-h] [-v] [-n NAME] [-l LOG] [-c] [-x] url

Create a wiki xml-dump via api.php

positional arguments:
  url                   download url

optional arguments:
  -h, --help            show this help message and exit
  -v, --verbose         verbose level... repeat up to three times
  -n NAME, --name NAME  name of the wiki for filename etc.
  -l LOG, --log LOG     specify log-file.
  -c, --compress        compress output file with bz2
  -x, --xowa            special XOWA mode: xml to stdout, progress to stderr

```


Example:

    ./mediawiki_api_dump.py http://wiki.archlinux.org
