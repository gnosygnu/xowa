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
package gplx.xowa; import gplx.*;
import gplx.core.strings.*;
public interface Sql_file_parser_cmd {
	void Exec(byte[] src, byte[] fld_key, int fld_idx, int fld_bgn, int fld_end, Bry_bfr file_bfr, Sql_file_parser_data data);
}
class Sql_file_parser_cmd_max_len implements Sql_file_parser_cmd {
	public void Log_len_(int v) {log_len = v;} private int log_len = 141;
	public void Log_print(Io_url url) {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < log.Count(); i++) {
			String itm = (String)log.Get_at(i);
			sb.Add(String_.Len(itm) + "|" + itm + "\n");
		}
		Io_mgr.I.SaveFilStr(url, sb.To_str());
	}
	public int Max_len() {return max_len;} private int max_len; 
	public void Exec(byte[] src, byte[] fld_key, int fld_idx, int fld_bgn, int fld_end, Bry_bfr file_bfr, Sql_file_parser_data data) {
		int fld_len = fld_end - fld_bgn;
		if (fld_len > max_len) max_len = fld_len;
		if (fld_len > log_len) {
			log.Add(String_.new_u8(src, fld_bgn, fld_end));
		}
		file_bfr.Add_mid(src, fld_bgn, fld_end).Add_byte(Byte_ascii.Pipe);
	}
	List_adp log = List_adp_.new_();
}
