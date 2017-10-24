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
public class Gflucene_idx_opt {
	public Gflucene_idx_opt(int uid, String key, String name) {
		this.uid = uid;
		this.key = key;
		this.name = name;
	}
	public int Uid() {return uid;} private final    int uid;
	public String Key() {return key;} private final    String key;
	public String Name() {return name;} private final    String name;

	public static final int 
	  Uid_docs = 0 // basic inverted word index; number of words is always 1 per doc
	, Uid_docs_and_freqs = 1 // freqs needed for number of words per doc
	, Uid_docs_and_freqs_and_positions = 2 // positions needed for proximity queries
	, Uid_docs_and_freqs_and_positions_and_offsets = 3 // offsets needed for highlighter
	;

	private static final    Hash_adp parse_hash = Hash_adp_.New();
	public static final    Gflucene_idx_opt
	  Docs = New(Uid_docs, "d", "Documents")
	, Docs_and_freqs = New(Uid_docs_and_freqs, "df", "Documents / Frequencies")
	, Docs_and_freqs_and_positions = New(Uid_docs_and_freqs_and_positions, "dfp", "Documents / Frequencies / Positions")
	, Docs_and_freqs_and_positions_and_offsets = New(Uid_docs_and_freqs_and_positions_and_offsets, "dfpo", "Documents / Frequencies / Positions / Offsets")
	;
	private static Gflucene_idx_opt New(int uid, String key, String name) {
		Gflucene_idx_opt rv = new Gflucene_idx_opt(uid, key, name);
		parse_hash.Add(key, rv);
		return rv;
	}
	public static Gflucene_idx_opt Parse(String key) {
		return (Gflucene_idx_opt)parse_hash.Get_by_or_fail(key);
	}
}
