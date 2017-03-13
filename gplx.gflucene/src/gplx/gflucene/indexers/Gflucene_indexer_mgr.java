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
package gplx.gflucene.indexers; import gplx.*; import gplx.gflucene.*;
import gplx.gflucene.core.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import gplx.gflucene.analyzers.*;;
public class Gflucene_indexer_mgr {
		private Analyzer analyzer;
    private IndexWriterConfig config;
	private Directory index;
    private IndexWriter wtr;
    private FieldType body_fld;
	
		public Gflucene_indexer_mgr() {
	}
	
	public void Init(Gflucene_index_data idx_data) {
				// create analyzer
		this.analyzer = Gflucene_analyzer_mgr_.New_analyzer(idx_data.analyzer_data.key);
		this.config = new IndexWriterConfig(analyzer);
		
		// create index
		Path path = Paths.get(idx_data.index_dir);
        try {
			this.index = FSDirectory.open(path);
		} catch (IOException e) {
			throw Err_.new_exc(e, "lucene_index", "failed to open lucene index", "path", path);
		}        

        // create writer
        try {
			wtr = new IndexWriter(index, config);
		} catch (IOException e) {
			throw Err_.new_exc(e, "lucene_index", "failed to create writer");
		}
        
        // create field for body
		this.body_fld = new FieldType();
		body_fld.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
//		body_fld.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
//		body_fld.setStored(true);
		body_fld.setTokenized(true);
//		body_fld.setStoreTermVectors(true);
//		body_fld.setStoreTermVectorOffsets(true);
        	}
	public void Exec(Gflucene_doc_data doc_data) {
		//		org.apache.lucene.document.
	    Document doc = new Document();
//	    doc.add(new SortedNumericDocValuesField("page_score", data.score));
	    doc.add(new StoredField("page_score", doc_data.score));
	    doc.add(new StoredField("page_id", doc_data.page_id));
	    doc.add(new TextField("title", doc_data.title, Field.Store.YES));
	    doc.add(new Field("body", doc_data.body, body_fld));
	    try {
			wtr.addDocument(doc);
		} catch (IOException e) {
			throw Err_.new_exc(e, "lucene_index", "failed to add document", "title", doc_data.title);
		}
			}
	public void Term() {
		        try {
			wtr.close();
		} catch (IOException e) {
			throw Err_.new_exc(e, "lucene_index", "failed to close writer");
		}
        try {
			index.close();
		} catch (IOException e) {
			throw Err_.new_exc(e, "lucene_index", "failed to close writer");
		}
			}
}
