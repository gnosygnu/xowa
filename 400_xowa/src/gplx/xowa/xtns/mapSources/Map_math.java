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
package gplx.xowa.xtns.mapSources; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
import gplx.core.btries.*;
class Map_math {// REF.MW:MapSources_math.php
	private int word_idx_nsew;
	private double[] rv = new double[4];
	private byte dir_id;
	private int prec;
	private int step;
	public double Dec() {return dec;} private double dec;
	public int Error() {return error;} private int error;
	public double Coord_dec() {return coord_dec;} private double coord_dec;
	public double Coord_deg() {return coord_deg;} private double coord_deg;
	public double Coord_min() {return coord_min;} private double coord_min;
	public double Coord_sec() {return coord_sec;} private double coord_sec;
	public byte[] Coord_dir_ns() {return coord_dir_ns;} private byte[] coord_dir_ns;
	public byte[] Coord_dir_ew() {return coord_dir_ew;} private byte[] coord_dir_ew;
	public boolean Ctor(byte[] input, int prec, byte[] dir, int until_step) {
		try {
			New_coord(input, dir, prec);
			if (until_step > 1 && error == 0)
				Set_coord();
		} catch (Exception e) {
			Err_.Noop(e);
			error = -128;
		}
		return error == 0;
	}
	public void Fail(Xop_ctx ctx, byte[] src, Xot_invk self, Bry_bfr bfr, byte[] pfunc_name) {
		String page_str = ctx.Cur_page().Url().Xto_full_str_safe();
		String pfunc_name_str = String_.new_utf8_(pfunc_name);
		String self_str = String_.new_utf8_(src, self.Src_bgn(), self.Src_end()); 
		switch (error) {
			case  -1:	// empty coord; EX: {{#deg2dd:|precision=6}}}} PAGE:it.v:Sami; DATE:2014-03-02
			case  -2:	// words > 4; EX:{{#geoLink: $1 $2 $3 $4 $5 $6|lat=51°20′00″19°55′50″}}; PAGE:pl.v:Rezerwat_przyrody_Jaksonek DATE:2014-08-14
			case  -3:	// invalid delim; EX:{{#geoLink: $1 $2 $3 $4 $5 $6|lat=51°31′37″|long=20°13′17'}}; PAGE:pl.v:Rezerwat_przyrody_Ciosny DATE:2014-08-14
				ctx.App().Usr_dlg().Log_many("", "", "mapSources failed: page=~{0} pfunc=~{1} err=~{2} src=~{3}", page_str, pfunc_name_str, error, self_str); // don't warn b/c there are many
				break;
			default:
				ctx.App().Usr_dlg().Warn_many("", "", "mapSources failed: page=~{0} pfunc=~{1} err=~{2} src=~{3}", page_str, pfunc_name_str, error, self_str);
				break;
		}
	}
	private void New_coord(byte[] input, byte[] dir, int prec) {	// REF.MW:newCoord
		this.error = 0; this.word_idx_nsew = -1;
		coord_dec = coord_deg = coord_min = coord_sec = 0;
		rv[0] = rv[1] = rv[2] = 0; rv[3] = 1;
		this.dir_id = Parse_dir(dir);
		this.prec = Parse_precision(prec);
		this.dec = 0;
		this.step = 0;
		Parse_input(input);
	}
	private void Set_coord() {	// REF.MW:setCoord
		if (step > 1) return;
		if (prec < 9)
			dec = Math_.Round(dec, prec);
		int sign = dec < 0 ? -1 : 1;
		double angle = Math_.Abs_double(dec);
		double deg = Math_.Floor(angle);
		double min = (angle - deg) * 60;
		double sec = prec > 0
			? Math_.Round((min - Math_.Floor(min)) * 60, prec - 4)
			: Math_.Round((min - Math_.Floor(min)) * 60, 0)
			;
		min = Math_.Floor(min);
		if (sec >= 60) {
			sec -= 60;
			min++;
		}
		if (prec < 3 && sec >= 30)
			min++;
		if (prec < 3)
			sec = 0;
		if (min >= 60) {
			min -= 60;
			deg++;
		}
		if (prec < 1 && min >= 30) {
			deg++;
		}
		if (prec < 1)
			min = 0;
		coord_dec = Math_.Round(dec, prec);
		coord_deg = deg * sign;
		coord_min = min;
		coord_sec = sec;
		if (sign > 0) {
			coord_dir_ns = Compass_N;
			coord_dir_ew = Compass_E;
		}
		else {
			coord_dir_ns = Compass_S;
			coord_dir_ew = Compass_W;
		}
		step = 2;
	}
	private static final byte[] Bry_deg = Bry_.new_utf8_("°"), Bry_quot = Bry_.new_ascii_("&quot;");
	public byte[] Get_dms(byte[] plus, byte[] minus) { // REF.MW: getDMSString
		if (step < 2) Set_coord();
		double deg = coord_deg;
		if (	dec < 0 
			&& (Bry_.Len_gt_0(plus) || Bry_.Len_gt_0(minus))) {
			deg = Math_.Abs_double(deg);
		}
		tmp_bfr.Add_double(deg).Add(Bry_deg);
		if (prec > 0)
			tmp_bfr.Add_byte_space().Add_double(coord_min).Add_byte(Byte_ascii.Apos);
		if (prec > 2)
			tmp_bfr.Add_byte_space().Add_double(coord_sec).Add(Bry_quot);
		byte[] letter = null;
		if (dir_id == Dir_lat_id)
			letter = coord_dir_ns;
		if (dir_id == Dir_long_id)
			letter = coord_dir_ew;
		if (dec > 0 && Bry_.Len_gt_0(plus))
			letter = plus;
		if (dec < 0 && Bry_.Len_gt_0(minus))
			letter = minus;
		if (letter != null)
			tmp_bfr.Add_byte_space().Add(letter);
		return tmp_bfr.Xto_bry_and_clear();
	}
	private void Parse_input(byte[] src) {	// REF.MW: toDec
		src = Parse_input_normalize(tmp_bfr, src);
		if (src == null) {error = -1; return;}
		int src_len = src.length;
		int word_idx = -1, word_bgn = 0, words_len = 0;
		int i = 0;			
		while (true) {
			boolean is_last = i == src_len;
			byte b = is_last ? Byte_ascii.Space : src[i];
			switch (b) {
				case Byte_ascii.Space:
					Parse_input_word(rv, src, ++word_idx, word_bgn, i);
					++words_len;
					i = Bry_finder.Find_fwd_while_space_or_tab(src, i, src_len);
					word_bgn = i;
					break;
			}
			if (is_last) break;
			i++;
		}
		if (words_len < 1 || words_len > 4) {error = -2; return;}
		if (word_idx_nsew != -1 && word_idx_nsew != words_len - 1) {error = -10; return;}
		if (rv[0] >= 0)
			dec = (rv[0] + rv[1] / 60 + rv[2] / 3600 ) * rv[3];
		else
			dec = (rv[0] - rv[1] / 60 - rv[2] / 3600 ) * rv[3];
		this.step = 1;
	}
	private boolean Parse_input_word_is_compass(byte v) {
		switch (v) {
			case Byte_ascii.Ltr_N:
			case Byte_ascii.Ltr_E:
			case Byte_ascii.Ltr_S:
			case Byte_ascii.Ltr_W:
				return true;
			default:
				return false;
		}
	}
	private void Parse_input_word(double[] rv, byte[] input, int word_idx, int word_bgn, int word_end) {
		if (word_idx >= Input_units_len) return;
		byte unit_dlm = Input_units[word_idx];
		int pos = Bry_finder.Find_fwd(input, unit_dlm, word_bgn, word_end);
		if (pos != Bry_.NotFound)	// remove dlms from end of bry; EX: "123'" -> "123"
			word_end = pos;
		if (!Parse_input_word_is_compass(input[word_bgn])) {	// if ( is_numeric( $v ) ) {
			double word_val = Bry_.XtoDoubleByPosOr(input, word_bgn, word_end, Double_.NaN);
			if (!Double_.IsNaN(word_val)) {
				if (word_idx > 2) {error = -4; return;}
				switch (word_idx) {
					case 0:
						if (word_val <= -180 || word_val > 180) {error = -5; return;}
						rv[0] = word_val;
						break;
					case 1:
						if (word_val < 0 || word_val >= 60) {error = -6; return;}
						if (rv[0] != (int)(rv[0])) {error = -7; return;}
						rv[1] = word_val;
						break;
					case 2:
						if (word_val < 0 || word_val >= 60) {error = -8; return;}
						if (rv[1] != (int)(rv[1])) {error = -9; return;}
						rv[2] = word_val;
						break;
				}
			}
			else {
				error = -3;
				return;
			}
		}
		else {	// 'NSEW'
			word_idx_nsew = word_idx;
			byte word_byte = input[word_bgn];
			if (rv[0] < 0) {error = -11; return;}
			if (word_end - word_bgn != 1) {error = -3; return;}
			switch (dir_id) {
				case Dir_long_id:
					if (word_byte == Byte_ascii.Ltr_N || word_byte == Byte_ascii.Ltr_S) {error = -12; return;}
					break;
				case Dir_lat_id:
					if (word_byte == Byte_ascii.Ltr_E || word_byte == Byte_ascii.Ltr_W) {error = -12; return;}
					break;
				case Dir_unknown_id:
					if (word_byte == Byte_ascii.Ltr_N || word_byte == Byte_ascii.Ltr_S)	this.dir_id = Dir_lat_id;
					else																this.dir_id = Dir_long_id;
					break;
			}
			if (this.dir_id == Dir_lat_id) {
				double rv_0 = rv[0];
				if (rv_0 < -90 || rv_0 > 90) {error = -13; return;}
			}
			if (word_byte == Byte_ascii.Ltr_S || word_byte == Byte_ascii.Ltr_W)
				rv[3] = -1;
		}
	}
	private static byte Parse_dir(byte[] dir) {
		if (Bry_.Len_eq_0(dir)) return Dir_unknown_id;
		Object dir_obj = Dir_trie.Match_bgn(dir, 0, dir.length);
		return dir_obj == null ? Dir_unknown_id : ((Byte_obj_val)dir_obj).Val();
	}
	private static int Parse_precision(int val) {	// REF.MW: MapSourcesMath.php|newCoord
		if		(val > -1 && val < 10)		return val;
		else if	(val == -1)					return 9;
		else								return 4;
	}
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(32);
	private static byte[] Parse_input_normalize(Bry_bfr bfr, byte[] input) {
		int input_end = input.length; if (input_end == 0) return null;
		int i = 0;
		while (i < input_end) {
			byte b = input[i];
			Object o = Input_trie.Match_bgn_w_byte(b, input, i, input_end);
			if (o == null) {
				bfr.Add_byte(b);
				++i;
			}
			else {
				byte tid = ((Byte_obj_val)o).Val();
				switch (tid) {
					case Input_tid_apos:			bfr.Add_byte(Byte_ascii.Apos).Add_byte_space(); break;				// EX: "'" -> "' "
					case Input_tid_quote:			bfr.Add_byte(Byte_ascii.Quote).Add_byte_space(); break;				// EX: '"' -> '" '
					case Input_tid_dash:			bfr.Add_byte(Byte_ascii.Dash); break;
					case Input_tid_space:			bfr.Add_byte_space(); break;
					case Input_tid_degree:			bfr.Add_byte(Byte_ascii.Slash); bfr.Add_byte_space(); break;		// EX: "°" -> "° "
					case Input_tid_compass:			bfr.Add_byte_space(); bfr.Add_byte(Byte_ascii.Case_upper(b)); break;	// NOTE: always single-char ASCII; EX: N,E,S,W,n,e,s,w
				}
				i = Input_trie.Match_pos();
			}
		}
		return bfr.Xto_bry_and_clear_and_trim();
	}
	private static final byte Dir_unknown_id = 0, Dir_lat_id = 1, Dir_long_id = 2;
	public static final byte[] Dir_lat_bry = Bry_.new_ascii_("lat"), Dir_long_bry = Bry_.new_ascii_("long");
	private static final Btrie_slim_mgr Dir_trie = Btrie_slim_mgr.ci_ascii_()	// NOTE:ci.ascii:MW_const.en
	.Add_bry_bval(Dir_lat_bry			, Dir_lat_id)
	.Add_bry_bval(Dir_long_bry			, Dir_long_id)
	;
	private static final byte[] 
	  Compass_N = new byte[] {Byte_ascii.Ltr_N}
	, Compass_E = new byte[] {Byte_ascii.Ltr_E}
	, Compass_S = new byte[] {Byte_ascii.Ltr_S}
	, Compass_W = new byte[] {Byte_ascii.Ltr_W}
	;
	private static final byte Input_tid_apos = 1, Input_tid_quote = 2, Input_tid_dash = 3, Input_tid_space = 4, Input_tid_degree = 5, Input_tid_compass = 6;
	private static final byte Input_byte_degree = Byte_ascii.Slash;	// NOTE: ugly cheat to avoid using multi-byte char; note that all "/" are swapped out to " ", so any remaining "/" was added by the normalizer; EX:  "123° 4/5" -> "123/ 4 5"
	private static final byte[] Input_units = new byte[] {Input_byte_degree, Byte_ascii.Apos, Byte_ascii.Quote, Byte_ascii.Space};
	private static final int Input_units_len = Input_units.length;
	private static final byte[] Input_bry_degree = Bry_.new_utf8_("°");
	private static final Btrie_slim_mgr Input_trie = Btrie_slim_mgr.cs_()
	.Add_str_byte("'"					, Input_tid_apos)		// NOTE: must add ' so that "'" -> "' "
	.Add_str_byte("‘"					, Input_tid_apos)
	.Add_str_byte("’"					, Input_tid_apos)
	.Add_str_byte("′"					, Input_tid_apos)
	.Add_str_byte("\""					, Input_tid_quote)		// NOTE: must add " so that '"' -> '" '
	.Add_str_byte("''"					, Input_tid_quote)
	.Add_str_byte("“"					, Input_tid_quote)
	.Add_str_byte("”"					, Input_tid_quote)
	.Add_str_byte("″"					, Input_tid_quote)
	.Add_str_byte("-"					, Input_tid_dash)
	.Add_str_byte("−"					, Input_tid_dash)
	.Add_str_byte(" "					, Input_tid_space)
	.Add_str_byte("_"					, Input_tid_space)
	.Add_str_byte("/"					, Input_tid_space)
	.Add_str_byte("\t"					, Input_tid_space)
	.Add_str_byte("\n"					, Input_tid_space)
	.Add_str_byte("\r"					, Input_tid_space)
	.Add_bry_bval(Input_bry_degree		, Input_tid_degree)
	.Add_str_byte("N"					, Input_tid_compass)
	.Add_str_byte("S"					, Input_tid_compass)
	.Add_str_byte("E"					, Input_tid_compass)
	.Add_str_byte("W"					, Input_tid_compass)
	.Add_str_byte("n"					, Input_tid_compass)
	.Add_str_byte("s"					, Input_tid_compass)
	.Add_str_byte("e"					, Input_tid_compass)
	.Add_str_byte("w"					, Input_tid_compass)
	;
	public static final Map_math _ = new Map_math();
}
