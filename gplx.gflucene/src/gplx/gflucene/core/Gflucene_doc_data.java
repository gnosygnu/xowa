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
package gplx.gflucene.core; import gplx.*; import gplx.gflucene.*;
public class Gflucene_doc_data {
	public String title;
	public String body;
	public int ns_id;
	public int page_id;
	public byte[] page_full_db;
	public int score;
	public float lucene_score = 0;
	public Gflucene_doc_data(int page_id, int score, String title, String body) {
		this.page_id = page_id;
		this.score = score;
		this.title = title;
		this.body = body;
	}
}
