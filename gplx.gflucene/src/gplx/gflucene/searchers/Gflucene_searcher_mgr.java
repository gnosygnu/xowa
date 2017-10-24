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
package gplx.gflucene.searchers; import gplx.*; import gplx.gflucene.*;
import gplx.gflucene.core.*;
import gplx.gflucene.analyzers.*;
import java.io.IOException;
import org.lukhnos.portmobile.file.Path;
import org.lukhnos.portmobile.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queries.CustomScoreQuery;
import org.apache.lucene.queries.function.FunctionQuery;
import org.apache.lucene.queries.function.valuesource.LongFieldSource;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import gplx.gflucene.indexers.*;
public class Gflucene_searcher_mgr {
		private Analyzer analyzer;
	private Directory index;
	
		public Gflucene_searcher_mgr() {
	}
	
	public void Init(Gflucene_index_data idx_data) {
				// create analyzer
		this.analyzer = Gflucene_analyzer_mgr_.New_analyzer(idx_data.analyzer_data.key); 

		// get index
		Path path = Paths.get(idx_data.index_dir);
		try {
			this.index = FSDirectory.open(path);
		} catch (IOException e) {
			throw Err_.new_exc(e, "lucene_index", "failed to init searcher", "dir", idx_data.index_dir);
		}
			}
	public void Exec(Ordered_hash list, Gflucene_searcher_qry data) {
				try {
			IndexReader reader = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader);

//			Query query = new QueryParser("body", analyzer).parse(data.query);
			
			// creates query that boosts by page_score; not sure if this is needed, but 1st release of fts uses this
			Query multi_query = MultiFieldQueryParser.parse(data.query, new String[] {"body"}, new BooleanClause.Occur []{BooleanClause.Occur.SHOULD}, analyzer);
			FunctionQuery boost_query = new FunctionQuery(new LongFieldSource("page_score"));			
			CustomScoreQuery query = new CustomScoreQuery(multi_query, boost_query);
 
			TopDocs docs = searcher.search(query, data.match_max);
			ScoreDoc[] hits = docs.scoreDocs;
			
			for(int i = 0; i < hits.length; i++) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
//				Gflucene_doc_data doc = new Gflucene_doc_data(Integer.parseInt(d.get("page_id")), Integer.parseInt(d.get("page_score")), d.get("title"), "");
				String docTitle = d.get("title");
				Gflucene_doc_data doc = (Gflucene_doc_data)list.Get_by(docTitle);
				if (doc == null) {
					int doc_id = Integer.parseInt(d.get("page_id"));
					doc = new Gflucene_doc_data(doc_id, 0, docTitle, "");
					doc.lucene_score = hits[i].score;
					list.Add(doc_id, doc);
				}
//				Tfds.Write(doc.lucene_score, doc.title);
			}
			
			reader.close();
		} catch (Exception e) {
			throw Err_.new_exc(e, "lucene_index", "failed to exec seearch", "query", data.query);
		}
		}
	public void Term() {
				}
}
