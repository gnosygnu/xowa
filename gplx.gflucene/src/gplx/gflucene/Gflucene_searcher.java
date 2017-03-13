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
package gplx.gflucene; import gplx.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
public class Gflucene_searcher {
		private final StandardAnalyzer analyzer = new StandardAnalyzer();
	private Directory index;
	
		public Gflucene_searcher() {
	}
	
	public void Init(String index_dir) {
				Path path = Paths.get(index_dir);
		try {
			this.index = FSDirectory.open(path);
		} catch (IOException e) {
			throw Err_.new_exc(e, "lucene_index", "failed to init searcher", "dir", index_dir);
		}
			}
	public void Exec(List_adp list, Gflucene_searcher_data data) {
				try {
			IndexReader reader = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader);
			
			
			Query query = new QueryParser("body", analyzer).parse(data.query);
//			TopDocs docs = searcher.search(query, reader.maxDoc());
			TopDocs docs = searcher.search(query, data.match_max);
			ScoreDoc[] hits = docs.scoreDocs;
			
			for(int i = 0; i < hits.length; i++) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				Gflucene_index_data doc = new Gflucene_index_data(Integer.parseInt(d.get("page_id")), Integer.parseInt(d.get("page_score")), d.get("title"), "");
				doc.lucene_score = hits[i].score;
				list.Add(doc);
			}
			
			reader.close();
		} catch (Exception e) {
			throw Err_.new_exc(e, "lucene_index", "failed to exec seearch", "query", data.query);
		}
		}
	public void Term() {
				}
}
