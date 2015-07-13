/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx;
public class Exc extends RuntimeException {
	private final String stack;
	private Exc_msg[] msgs_ary = new Exc_msg[8]; private int msgs_len = 8, msgs_idx = 0;
	public Exc(String stack, String type, String msg, Object... args) {
		Msgs_add(type, msg, args);
		this.stack = stack;
	}
	public int Stack_erase() {return stack_erase;} public Exc Stack_erase_1_() {stack_erase = 1; return this;} private int stack_erase = 0;
	public Exc Args_add(Object... args) {msgs_ary[msgs_idx - 1].Args_add(args); return this;}	// i - 1 to get current
	@gplx.Internal protected boolean Type_match(String type) {
		for (int i = 0; i < msgs_len; ++i) {
			if (String_.Eq(type, msgs_ary[i].Type())) return true;
		}
		return false;
	}
	@gplx.Internal protected void Msgs_add(String type, String msg, Object[] args) {
		if (msgs_idx == msgs_len) {
			int new_len = msgs_len * 2;
			Exc_msg[] new_ary = new Exc_msg[new_len];
			Array_.CopyTo(msgs_ary, new_ary, 0);
			this.msgs_ary = new_ary;
			this.msgs_len = new_len;
		}
		msgs_ary[msgs_idx] = new Exc_msg(type, msg, args);
		++msgs_idx;
	}
	public String To_str_all() {
		String rv = "";
		for (int i = msgs_idx - 1; i > -1; --i) {
			rv += "[err " + Int_.Xto_str(i) + "] " + msgs_ary[i].To_str() + "\n";
		}
		rv += "[stack]\n  " + Stack_to_str(this, stack);
		return rv;
	}
	@Override public String getMessage() {return To_str_all();}
	private static String Stack_to_str(Exception e, String stack) {
		String rv = stack;
		if (rv == Exc_.Stack_null) {	// occurs for thrown gplx exceptions; EX: throw Exc_.new_unimplemented
			rv = "";					// set to "" b/c String concat below;
			String[] lines = String_.Split_lang(Exc_.Stack_lang(e), '\n');
			int len = lines.length;
			for (int i = 0; i < len; ++i) {
				String line = lines[i];
				if (String_.Has_at_bgn(line, "gplx.Exc_.new")) continue;	// ignore stack frames with "gplx.Exc_.new"; EX: throw Exc_.new_unimplemented
				if (String_.Len(rv) > 0) rv += "\n";
				rv += line;
			}
		}			
		rv = String_.Replace(rv, "\n", "\n  ");	// "  " is to indent stack stack
		return rv;
	}
}
