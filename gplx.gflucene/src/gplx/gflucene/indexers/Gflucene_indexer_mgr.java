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
import org.lukhnos.portmobile.file.Path;
import org.lukhnos.portmobile.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.TieredMergePolicy;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import gplx.gflucene.analyzers.*;;
public class Gflucene_indexer_mgr {
		private Analyzer analyzer;
    private IndexWriterConfig config;
	private Directory index;
    private IndexWriter wtr;
    private FieldType body_fld_type;
	
		public Gflucene_indexer_mgr() {
	}
	
	public void Init(Gflucene_index_data idx_data, String idx_opt) {
				// create analyzer
		this.analyzer = Gflucene_analyzer_mgr_.New_analyzer(idx_data.analyzer_data.key);
		this.config = new IndexWriterConfig(analyzer);
		
		// limit max size by setting merge policy
		TieredMergePolicy merge_policy = new TieredMergePolicy();
		merge_policy.setMaxMergedSegmentMB(idx_data.max_merged_segments);
		config.setMergePolicy(merge_policy);
		
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
		this.body_fld_type = new FieldType();
		IndexOptions index_options = To_index_options(idx_opt);
		body_fld_type.setIndexOptions(index_options);
		body_fld_type.setTokenized(true);
		body_fld_type.setStored(false);
//		body_fld.setStoreTermVectors(true);
//		body_fld.setStoreTermVectorOffsets(true);
        	}
	public void Exec(Gflucene_doc_data doc_data) {
			    Document doc = new Document();
	    
	    doc.add(new StoredField("page_id", doc_data.page_id));
	    doc.add(new NumericDocValuesField("page_score", doc_data.score));

//	    float score = ((float)doc_data.score / 1000000);
//	    float score = doc_data.score;

	    TextField title_field = new TextField("title", doc_data.title, Field.Store.YES);
//	    title_field.setBoost(score * 1024);
//	    title_field.setBoost(score);
	    doc.add(title_field);
	    
	    Field body_field = new Field("body", doc_data.body, body_fld_type);
//	    body_field.setBoost(score);
	    doc.add(body_field);
	    
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

		private static IndexOptions To_index_options(String key) {
		Gflucene_idx_opt opt = Gflucene_idx_opt.Parse(key);
		switch (opt.Uid()) {
			case Gflucene_idx_opt.Uid_docs:                                     return IndexOptions.DOCS;
			case Gflucene_idx_opt.Uid_docs_and_freqs:                           return IndexOptions.DOCS_AND_FREQS;
			case Gflucene_idx_opt.Uid_docs_and_freqs_and_positions:             return IndexOptions.DOCS_AND_FREQS_AND_POSITIONS;
			case Gflucene_idx_opt.Uid_docs_and_freqs_and_positions_and_offsets: return IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS;
			default:                                                            throw Err_.new_unhandled_default(opt.Uid());
		}
	}
	}
