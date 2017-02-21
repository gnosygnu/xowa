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
package gplx.xowa.htmls.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.modules.*;
import gplx.xowa.apps.apis.xowa.html.modules.*;
public class Xow_popup_cfg {
	public int Show_all_if_less_than() {return show_all_if_less_than;} public void Show_all_if_less_than_(int v) {show_all_if_less_than = v;} private int show_all_if_less_than;
	public int Tmpl_read_max() {return tmpl_read_max;} public void Tmpl_read_max_(int v) {tmpl_read_max = v;} private int tmpl_read_max;
	public int Tmpl_read_len() {return tmpl_read_len;} public void Tmpl_read_len_(int v) {tmpl_read_len = v;} private int tmpl_read_len;
	public int Read_til_stop_fwd() {return read_til_stop_fwd;} public void Read_til_stop_fwd_(int v) {read_til_stop_fwd = v;} private int read_til_stop_fwd;
	public int Read_til_stop_bwd() {return read_til_stop_bwd;} public void Read_til_stop_bwd_(int v) {read_til_stop_bwd = v;} private int read_til_stop_bwd;
	public int Stop_if_hdr_after() {return stop_if_hdr_after;} public void Stop_if_hdr_after_(int v) {stop_if_hdr_after = v;} private int stop_if_hdr_after;
	public boolean Stop_if_hdr_after_enabled() {return stop_if_hdr_after > 0;}
	public byte[] Ellipsis() {return ellipsis;} public void Ellipsis_(byte[] v) {ellipsis = v;} private byte[] ellipsis = Bry_.Empty;
	public byte[] Notoc() {return notoc;} public void Notoc_(byte[] v) {notoc = v;} private byte[] notoc = Notoc_const;
	public static final    byte[]
	  Notoc_const		= Bry_.new_a7("\n__NOTOC__") // NOTE: always add a whitespace tkn else __NOTOC__ will be deactivated if last tkn is lnke; DATE:2014-06-22
	, Msg_key_ellipsis	= Bry_.new_a7("ellipsis")
	;
}
