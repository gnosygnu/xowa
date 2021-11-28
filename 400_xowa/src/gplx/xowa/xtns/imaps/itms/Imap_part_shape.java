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
package gplx.xowa.xtns.imaps.itms; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.imaps.*;
import gplx.core.primitives.*;
import gplx.xowa.parsers.*;
public class Imap_part_shape implements Imap_part, Imap_link_owner {
	public Imap_part_shape(byte shape_tid, Double_obj_val[] shape_pts) {
		this.shape_tid = shape_tid;
		this.shape_pts = shape_pts;
	}
	public byte				Part_tid() {return shape_tid;} private final byte shape_tid;
	public Double_obj_val[] Shape_pts() {return shape_pts;} private final Double_obj_val[] shape_pts;
	public int				Link_tid() {return link_tid;} private int link_tid;
	public Xop_tkn_itm		Link_tkn() {return link_tkn;} private Xop_tkn_itm link_tkn; public void	Link_tid_(int tid, Xop_tkn_itm tkn) {link_tid = tid; link_tkn = tkn;} 
	public byte[]			Link_href() {return link_href;} private byte[] link_href;	public void Link_href_(byte[] v) {this.link_href = v;}
	public byte[]			Link_text() {return link_text;} private byte[] link_text;	public void Link_text_(byte[] v) {this.link_text = v;}

	public static final byte Tid_default = 0, Tid_rect = 4, Tid_circle = 3, Tid_poly = 5;
}
