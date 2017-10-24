/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.gflucene.highlighters; import gplx.*; import gplx.gflucene.*;
import gplx.gflucene.core.*;
import gplx.gflucene.analyzers.*;
import gplx.gflucene.searchers.*;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TextFragment;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.FSDirectory;
public class Gflucene_highlighter_mgr {
		private Analyzer analyzer;
	
		public Gflucene_highlighter_mgr() {
	}
	
	public void Init(Gflucene_index_data idx_data) {
				this.analyzer = Gflucene_analyzer_mgr_.New_analyzer(idx_data.analyzer_data.key);
			}
	public Gflucene_highlighter_item[] Exec(Gflucene_searcher_qry qry_data, Gflucene_doc_data doc_data) {
				// create query
		QueryParser parser = new QueryParser("body", analyzer);
		Query query = null;
		try {
			query = parser.parse(qry_data.query);
		} catch (ParseException e) {
			throw Err_.new_exc(e, "lucene_index", "failed to parse", "query", qry_data.query);
		}
		
		// create highlighter
		SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<span class='snip_highlight'>", "</span>");
		QueryScorer scorer = new QueryScorer(query);
		scorer.setExpandMultiTermQuery(false);
		Highlighter highlighter = new Highlighter(htmlFormatter, scorer);
		SimpleFragmenter fragmenter = new SimpleFragmenter(100);
		highlighter.setTextFragmenter(fragmenter);

		// get token stream
		String text = doc_data.body;
		TokenStream tokenStream = null;
		try {
			tokenStream = analyzer.tokenStream("body", text);
		} catch (IOException e) {
			throw Err_.new_exc(e, "lucene_index", "failed to get stream", "query", qry_data.query);
		}
		
		// get fragments from stream
		TextFragment[] frags;
		try {
//			frags = highlighter.getBestTextFragments(tokenStream, text, false, 1000);
			frags = highlighter.getBestTextFragments(tokenStream, text, true, 10);
		} catch (IOException e) {
			throw Err_.new_exc(e, "lucene_index", "failed to get best", "query", qry_data.query);
		} catch (InvalidTokenOffsetsException e) {
			throw Err_.new_exc(e, "lucene_index", "failed to get best", "query", qry_data.query);
		}
		
		// convert fragments to highlighter items
		int frags_len = frags.length;
		Gflucene_highlighter_item[] array = new Gflucene_highlighter_item[frags_len];
		for (int i = 0; i < frags_len; i++) {
			String frag = frags[i].toString();
			array[i] = new Gflucene_highlighter_item(i, frag);
		}
		return array;
			}
	public void Term() {
					}
}
