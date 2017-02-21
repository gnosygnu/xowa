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
package gplx.xowa.apps.servers.tcp; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*;
public class Xosrv_msg {
	public byte Version_tid() {return Version_tid_0;} static final byte Version_tid_0 = 0;
	public byte[] Cmd_name() {return cmd_name;} private byte[] cmd_name;
	public byte[] Msg_id() {return msg_id;} private byte[] msg_id;
	public byte[] Sender() {return sender;} private byte[] sender;
	public byte[] Recipient() {return recipient;} private byte[] recipient;
	public byte[] Msg_date() {return msg_date;} private byte[] msg_date;
	public byte[] Msg_text() {return msg_text;} private byte[] msg_text;
	public void Print(Bry_bfr bfr) {
		int body_len = cmd_name.length + msg_id.length + sender.length + recipient.length + msg_date.length + msg_text.length + 5;	// 5=5 pipes for 6 fields
		int cksum = (body_len * 2) + 1; 
		bfr.Add_int_fixed(this.Version_tid()	,  1).Add_byte_pipe();		// 0|
		bfr.Add_int_fixed(body_len				, 10).Add_byte_pipe();		// 0123456789|
		bfr.Add_int_fixed(cksum					, 10).Add_byte_pipe();		// 0123456789|
		bfr.Add(cmd_name							).Add_byte_pipe();		// cmd|
		bfr.Add(msg_id								).Add_byte_pipe();		// id|
		bfr.Add(sender								).Add_byte_pipe();		// sender|
		bfr.Add(recipient							).Add_byte_pipe();		// recipient|
		bfr.Add(msg_date							).Add_byte_pipe();		// msg_date|
		bfr.Add(msg_text							);						// msg_text
	}
	public static final Xosrv_msg Exit = new Xosrv_msg();
	public static Xosrv_msg fail_(String fmt, Object... ary) {
		Xosrv_msg rv = new Xosrv_msg();
		rv.msg_text = Bry_.new_u8(String_.Format(fmt, ary));
		return rv;
	}
	public static Xosrv_msg new_(byte[] cmd_name, byte[] msg_id, byte[] sender, byte[] recipient, byte[] msg_date, byte[] msg_text) {
		Xosrv_msg rv = new Xosrv_msg();
		rv.cmd_name = cmd_name;
		rv.msg_id = msg_id;
		rv.sender = sender;
		rv.recipient = recipient;
		rv.msg_date = msg_date;
		rv.msg_text = msg_text;
		return rv;
	}
}
/*
Message definition
  Id            : 0
  Purpose       : Version number for message format
  Data type     : int
  Notes         : Always 0; will change to 1 if message format ever changes
  Example       : "0"

  Id            : 1
  Description   : Body length; specified total length of message field 3 (body)
  Data type     : int
  Notes         : always zero-padded to 10 bytes (not hexadecimal) 
  Example       : "0000000123"

  Id            : 2
  Description   : Checksum; should equal (2 * body length) + 1
  Data type     : int
  Notes         : always zero-padded to 10 bytes (not hexadecimal) 
  Example       : "0000000247"

  Id            : 3
  Description   : Body
  Data type     : String
  Notes         : length specified by field 1 (body length)
  Example       : see below

Body definition
  * Pipes are not allowed in any field except for the last
  * Only the first field is required

  Id            : 0
  Purpose       : Command name
  Data type     : String
  Notes         : unique name identifying the command
  Example       : "xowa.cmd.exec", "xowa.cmd.result", "xowa.cmd.error", "xowa.js.exec", "xowa.js.result", "xowa.js.error"

  Id            : 1
  Purpose       : Message id
  Data type     : String
  Notes         : Usage is defined by callers; can be empty
  Example       : "1", ""

  Id            : 2
  Purpose       : Sender id
  Data type     : String
  Notes         : Usage is defined by callers; can be empty
  Example       : "tab1", "xowa", ""

  Id            : 3
  Purpose       : Recipient id
  Data type     : String
  Notes         : Usage is defined by callers; can be empty
  Example       : "xowa", "tab1", ""

  Id            : 4
  Purpose       : Message date
  Data type     : String
  Notes         : ISO 8601 format; see http://www.w3.org/TR/NOTE-datetime; Usage is defined by callers; can be empty
  Example       : "1997-07-16T19:20:30.45+01:00", ""

  Id            : 5
  Purpose       : Message text
  Data type     : String
  Notes         : freeform; can contain any character
  Example       : "app.shell.fetch_page('simple.wikipedia.org/wiki/Earth', 'html');"*/
