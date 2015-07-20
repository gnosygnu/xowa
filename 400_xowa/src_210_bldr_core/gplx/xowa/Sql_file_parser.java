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
import gplx.core.flds.*; import gplx.ios.*;
public class Sql_file_parser {
	public Io_url Src_fil() {return src_fil;} public Sql_file_parser Src_fil_(Io_url v) {src_fil = v; return this;} Io_url src_fil;
	public int Src_len() {return src_len;} public Sql_file_parser Src_len_(int v) {src_len = v; return this;} private int src_len = 8 * Io_mgr.Len_mb;
	public Io_url_gen Trg_fil_gen() {return trg_fil_gen;} public Sql_file_parser Trg_fil_gen_(Io_url_gen v) {trg_fil_gen = v; return this;} Io_url_gen trg_fil_gen;
	public int Trg_len() {return trg_len;} public Sql_file_parser Trg_len_(int v) {trg_len = v; return this;} private int trg_len = 4 * Io_mgr.Len_mb;
	private Sql_fld_itm[] flds_all; private int flds_all_len;
	Gfo_fld_rdr sql_parser = Gfo_fld_rdr.sql_(); Gfo_fld_wtr fld_wtr = Gfo_fld_wtr.xowa_();
	static final byte Mode_sql_bgn = 0, Mode_row_bgn = 1, Mode_row_end = 2, Mode_fld = 3, Mode_quote = 4, Mode_escape = 5;
	public Sql_file_parser Fld_cmd_(Sql_file_parser_cmd v) {this.fld_cmd = v; return this;} Sql_file_parser_cmd fld_cmd;
	public Sql_file_parser Flds_req_(byte[]... v) {flds_req = v; return this;} private byte[][] flds_req;
	public Sql_file_parser Flds_req_idx_(int flds_all_len, int... idxs) {
		new_flds_all(flds_all_len);
		int len = idxs.length;
		for (int i = 0; i < len; i++) {
			int idx = idxs[i];
			Sql_fld_itm itm = new Sql_fld_itm(idx, Bry_.Empty);
			flds_all[idx] = itm;
		} 
		return this;
	}
	private void Identify_flds(byte[] raw) {
		Sql_fld_mgr fld_mgr = new Sql_fld_mgr().Parse(raw);
		new_flds_all(fld_mgr.Count());
		int len = flds_req.length;
		for (int i = 0; i < len; i++) {
			byte[] fld = flds_req[i];
			Sql_fld_itm itm = fld_mgr.Get_by_key(fld); if (itm == null) throw Err_.new_wo_type("could not find field", "fld", fld);
			flds_all[itm.Idx()] = itm;
		} 
	}
	private void new_flds_all(int len) {
		this.flds_all_len = len;	// NOTE: must set flds_all_len, else Commit_fld will not be correct;
		this.flds_all = new Sql_fld_itm[len];
	}
	public void Parse(Gfo_usr_dlg usr_dlg) {
		Io_buffer_rdr rdr = Io_buffer_rdr.Null;
		try {
			rdr = Io_buffer_rdr.new_(gplx.ios.Io_stream_rdr_.new_by_url_(src_fil), src_len);
			Bry_bfr fil_bfr = Bry_bfr.new_(), val_bfr = Bry_bfr.new_();
			byte[] bfr = rdr.Bfr(); int bfr_len = rdr.Bfr_len(), fld_idx = 0, cur_pos = 0;
			if (flds_req != null) Identify_flds(bfr);
			byte mode = Mode_sql_bgn; byte[] decode_regy = sql_parser.Escape_decode();
			boolean reading_file = true; byte mode_prv = Mode_sql_bgn;
			Sql_file_parser_data data = new Sql_file_parser_data();
			while (reading_file) {
				if (cur_pos + 256 > bfr_len && rdr.Fil_pos() != rdr.Fil_len()) {	// buffer 256 characters; can be 0, but erring on side of simplicity
					rdr.Bfr_load_from(cur_pos);
					cur_pos = 0;
					bfr = rdr.Bfr();
					bfr_len = rdr.Bfr_len();
				}
				if (cur_pos == bfr_len) break;
				byte b = bfr[cur_pos];
				switch (mode) {
					case Mode_sql_bgn:
						cur_pos = Bry_finder.Find_fwd(bfr, Bry_insert_into, cur_pos);
						if (cur_pos == Bry_.NotFound || cur_pos > bfr_len) {reading_file = false; continue;}
						cur_pos = Bry_finder.Find_fwd(bfr, Bry_values, cur_pos);
						if (cur_pos == Bry_.NotFound || cur_pos > bfr_len) throw Err_.new_wo_type("VALUES not found");	// something went wrong;
						mode = Mode_fld;
						cur_pos += Bry_values.length;
						break;
					case Mode_row_bgn:
						switch (b) {
							case Byte_ascii.Paren_bgn:		mode = Mode_fld; break;
							default:						throw Err_.new_unhandled(mode);
						}
						++cur_pos;
						break;
					case Mode_row_end:
						switch (b) {
							case Byte_ascii.Nl:		break;	// ignore \n
							case Byte_ascii.Comma:			mode = Mode_row_bgn; break;
							case Byte_ascii.Semic:			mode = Mode_sql_bgn; break;
							default:						throw Err_.new_unhandled(mode);
						}
						++cur_pos;
						break;
					case Mode_fld:
						switch (b) {
							case Byte_ascii.Apos:			mode = Mode_quote;	break;	// NOTE: never escape apos by doubling; will fail for empty fields; EX: ", '', ''"; DATE:2013-07-06
							case Byte_ascii.Backslash:		mode_prv = mode; mode = Mode_escape; break;
							default:						val_bfr.Add_byte(b); break;
							case Byte_ascii.Space: case Byte_ascii.Nl:	break;
							case Byte_ascii.Comma:
								Commit_fld(fld_idx++, val_bfr, fil_bfr, data);
								break;
							case Byte_ascii.Paren_end:
								Commit_fld(fld_idx++, val_bfr, fil_bfr, data);
								if (!data.Cancel_row())
									Commit_row(usr_dlg, fil_bfr);
								fld_idx = 0;
								mode = Mode_row_end;
								break;
						}
						++cur_pos;
						break;
					case Mode_quote:
						switch (b) {
							case Byte_ascii.Apos:			mode = Mode_fld; break;
							case Byte_ascii.Backslash:		mode_prv = mode; mode = Mode_escape; break;
							default:						val_bfr.Add_byte(b); break;
						}
						++cur_pos;
						break;
					case Mode_escape:
						byte escape_val = decode_regy[b];
						if (escape_val == Byte_ascii.Null)	{val_bfr.Add_byte(Byte_ascii.Backslash).Add_byte(b);}
						else								val_bfr.Add_byte(escape_val);
						mode = mode_prv;
						++cur_pos;
						break;
					default:								throw Err_.new_unhandled(mode);
				}
			}
			Io_mgr.I.AppendFilByt(trg_fil_gen.Nxt_url(), fil_bfr.Xto_bry_and_clear());
		}
		finally {rdr.Rls();}
	}
	private void Commit_row(Gfo_usr_dlg usr_dlg, Bry_bfr fil_bfr) {
		fil_bfr.Add_byte(Byte_ascii.Nl);
		if (fil_bfr.Len() > trg_len) {
			Io_url trg_fil = trg_fil_gen.Nxt_url();				
			usr_dlg.Prog_one(GRP_KEY, "make", "making ~{0}", trg_fil.NameAndExt());
			Io_mgr.I.AppendFilByt(trg_fil, fil_bfr.Xto_bry_and_clear());
		}
	}
	private void Commit_fld(int fld_idx, Bry_bfr val_bfr, Bry_bfr fil_bfr, Sql_file_parser_data data) {
		Sql_fld_itm fld = fld_idx < flds_all_len ? flds_all[fld_idx] : null;	// handle new flds added by MW, but not supported by XO; EX:hiddencat and pp_sortkey; DATE:2014-04-28
		if (fld != null) {
			data.Cancel_row_n_();
			if (fld_cmd == null) {	// no custom cmd; assume append;
				fld_wtr.Bfr_(fil_bfr);
				fld_wtr.Write_bry_escape_fld(val_bfr.Bfr(), 0, val_bfr.Len());
			}
			else
				fld_cmd.Exec(val_bfr.Bfr(), fld.Key(), fld_idx, 0, val_bfr.Len(), fil_bfr, data);
		}
		val_bfr.Clear();
	}
	private static final byte[] Bry_insert_into = Bry_.new_a7("INSERT INTO "), Bry_values = Bry_.new_a7(" VALUES (");
	private static final String GRP_KEY = "xowa.bldr.sql";
}
