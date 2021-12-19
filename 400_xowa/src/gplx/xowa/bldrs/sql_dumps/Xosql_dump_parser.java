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
package gplx.xowa.bldrs.sql_dumps;
import gplx.core.flds.Gfo_fld_rdr;
import gplx.core.ios.Io_buffer_rdr;
import gplx.core.ios.streams.Io_stream_rdr_;
import gplx.libs.dlgs.Gfo_usr_dlg;
import gplx.libs.files.Io_url;
import gplx.libs.ios.IoConsts;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.utls.IntUtl;
import gplx.types.errs.ErrUtl;
public class Xosql_dump_parser {
	private Xosql_dump_cbk cbk;
	private Io_url src_fil; private int src_rdr_bfr_len = 8 * IoConsts.LenMB;
	private Xosql_fld_hash cbk_flds;
	private Ordered_hash tbl_flds;
	public Xosql_dump_parser(Xosql_dump_cbk cbk, String... cbk_keys) {
		this.cbk = cbk;
		this.cbk_flds = Xosql_fld_hash.New(cbk_keys);
	}
	public void Src_fil_(Io_url v) {this.src_fil = v;}
	public void Parse(Gfo_usr_dlg usr_dlg) {
		Io_buffer_rdr rdr = Io_buffer_rdr.Null;
		try {
			// init bfrs, rdr
			BryWtr val_bfr = BryWtr.New();
			rdr = Io_buffer_rdr.new_(Io_stream_rdr_.New_by_url(src_fil), src_rdr_bfr_len);
			byte[] bfr = rdr.Bfr(); int bfr_len = rdr.Bfr_len(), fld_idx = 0, cur_pos = 0;

			this.tbl_flds = Identify_flds(cbk_flds, bfr);

			// init fld_rdr
			Gfo_fld_rdr fld_rdr = Gfo_fld_rdr.sql_();
			byte[] decode_regy = fld_rdr.Escape_decode();

			byte mode_prv = Mode__sql_bgn; byte mode = Mode__sql_bgn;
			boolean reading_file = true;
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
					case Mode__sql_bgn:// skip over header to 1st "VALUES"
						cur_pos = BryFind.FindFwd(bfr, Bry_insert_into, cur_pos);
						if (cur_pos == BryFind.NotFound || cur_pos > bfr_len) {reading_file = false; continue;}
						cur_pos = BryFind.FindFwd(bfr, Bry_values, cur_pos);
						if (cur_pos == BryFind.NotFound || cur_pos > bfr_len) throw ErrUtl.NewArgs("VALUES not found");	// something went wrong;
						mode = Mode__fld;
						cur_pos += Bry_values.length;
						break;
					case Mode__row_bgn: // assert "("
						switch (b) {
							case AsciiByte.ParenBgn:		mode = Mode__fld; break;
							default:						throw ErrUtl.NewUnhandled(mode);
						}
						++cur_pos;
						break;
					case Mode__row_end:	// handle 1st char after ")";
						switch (b) {
							case AsciiByte.Nl:				break;	// ignore \n
							case AsciiByte.Comma:			mode = Mode__row_bgn; break;	// handle ","; EX: "(1),(2)"
							case AsciiByte.Semic:			mode = Mode__sql_bgn; break;	// handle ";"; EX: "(1);INSERT INTO"
							default:						throw ErrUtl.NewUnhandled(mode);
						}
						++cur_pos;
						break;
					case Mode__fld:		// handle fld chars; EX: "(1,'ab')"
						switch (b) {
							case AsciiByte.Space:			// ws: skip; EX: "(1 , 2)"; "(1,\n2)"
							case AsciiByte.Nl:
								break;
							case AsciiByte.Apos:			// apos: switch modes; NOTE: never escape apos by doubling; will fail for empty fields; EX: ", '', ''"; DATE:2013-07-06
								mode = Mode__quote;
								break;
							case AsciiByte.Backslash:		// backslash: switch modes;
								mode_prv = mode;
								mode = Mode__escape;
								break;
							case AsciiByte.Comma:			// comma: end fld
								Commit_fld(fld_idx++, val_bfr);
								break;
							case AsciiByte.ParenEnd:		// paren_end: end fld and row
								Commit_fld(fld_idx++, val_bfr);
								cbk.On_row_done();
								fld_idx = 0;
								mode = Mode__row_end;
								break;
							default:						// all other chars; add to val_bfr
								val_bfr.AddByte(b);
								break;
						}
						++cur_pos;
						break;
					case Mode__quote:	// add to val_bfr until quote encountered; also, handle backslashes;
						switch (b) {
							case AsciiByte.Apos:			mode = Mode__fld; break;
							case AsciiByte.Backslash:		mode_prv = mode; mode = Mode__escape; break;
							default:						val_bfr.AddByte(b); break;
						}
						++cur_pos;
						break;
					case Mode__escape:	// get escape_val from decode_regy; if unknown, just add original
						byte escape_val = decode_regy[b];
						if (escape_val == AsciiByte.Null)
							val_bfr.AddByte(AsciiByte.Backslash).AddByte(b);
						else
							val_bfr.AddByte(escape_val);
						mode = mode_prv;	// switch back to prv_mode
						++cur_pos;
						break;
					default:								throw ErrUtl.NewUnhandled(mode);
				}
			}
		}
		finally {rdr.Rls();}
	}
	private void Commit_fld(int fld_idx, BryWtr val_bfr) {
		Xosql_fld_itm fld = (Xosql_fld_itm)tbl_flds.GetAt(fld_idx);	// handle new flds added by MW, but not supported by XO; EX:hiddencat and pp_sortkey; DATE:2014-04-28
		if (fld.Uid() != IntUtl.MaxValue)
			cbk.On_fld_done(fld.Uid(), val_bfr.Bry(), 0, val_bfr.Len());
		val_bfr.Clear();
	}
	private static Ordered_hash Identify_flds(Xosql_fld_hash cbk_hash, byte[] raw) {			
		// parse tbl def
		Xosql_tbl_parser tbl_parser = new Xosql_tbl_parser();
		Ordered_hash tbl_flds = tbl_parser.Parse(raw);

		// loop over tbl_flds
		int len = tbl_flds.Len();
		for (int i = 0; i < len; ++i) {
			Xosql_fld_itm tbl_itm = (Xosql_fld_itm)tbl_flds.GetAt(i);
			// get cbk_itm
			Xosql_fld_itm cbk_itm = cbk_hash.Get_by_key(tbl_itm.Key());
			if (cbk_itm == null) continue;// throw Err_.New("sql_dump_parser: failed to find fld; src={0} fld={1}", src_fil.Raw(), tbl_itm.Key());

			// set tbl_def's uid to cbk_itm's uid
			tbl_itm.Uid_(cbk_itm.Uid());
		}

		tbl_flds.Sort();
		return tbl_flds;
	}
	public Xosql_dump_parser Src_rdr_bfr_len_(int v) {src_rdr_bfr_len = v; return this;}	// TEST:

	private static final byte[] Bry_insert_into = BryUtl.NewA7("INSERT INTO "), Bry_values = BryUtl.NewA7(" VALUES (");
	private static final byte Mode__sql_bgn = 0, Mode__row_bgn = 1, Mode__row_end = 2, Mode__fld = 3, Mode__quote = 4, Mode__escape = 5;
}
