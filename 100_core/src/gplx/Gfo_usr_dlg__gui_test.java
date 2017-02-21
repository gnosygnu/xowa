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
package gplx;
import gplx.core.lists.rings.*;
public class Gfo_usr_dlg__gui_test implements Gfo_usr_dlg__gui {
	public List_adp Warns() {return warns;} private final    List_adp warns = List_adp_.New();
	public List_adp Msgs() {return msgs;} private final    List_adp msgs = List_adp_.New();
	public Ring__string Prog_msgs() {return ring;} private final    Ring__string ring = new Ring__string().Max_(0);
	public void Clear() {msgs.Clear(); warns.Clear();}
	public void Write_prog(String text) {msgs.Add(text);}
	public void Write_note(String text) {msgs.Add(text);}
	public void Write_warn(String text) {warns.Add(text);} 
	public void Write_stop(String text) {msgs.Add(text);}
}
