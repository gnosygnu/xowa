/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.mapSources;

import gplx.core.btries.Btrie_slim_mgr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.MathUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.wrappers.ByteVal;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.tmpls.Xot_invk;

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
			error = -128;
		}
		return error == 0;
	}
	public void Fail(Xop_ctx ctx, byte[] src, Xot_invk self, BryWtr bfr, byte[] pfunc_name) {
		String page_str = ctx.Page().Url().To_str();
		String pfunc_name_str = StringUtl.NewU8(pfunc_name);
		String self_str = StringUtl.NewU8(src, self.Src_bgn(), self.Src_end());
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
			dec = MathUtl.Round(dec, prec);
		int sign = dec < 0 ? -1 : 1;
		double angle = MathUtl.AbsAsDouble(dec);
		double deg = MathUtl.Floor(angle);
		double min = (angle - deg) * 60;
		double sec = prec > 4 // 2020-09-03|ISSUE#:792|precision check should be > 4 not > 0;PAGE:en.w:Huntington_Plaza
			? MathUtl.Round((min - MathUtl.Floor(min)) * 60, prec - 4)
			: MathUtl.Round((min - MathUtl.Floor(min)) * 60, 0)
			;
		min = MathUtl.Floor(min);
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
		coord_dec = MathUtl.Round(dec, prec);
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
	public byte[] Get_dms(boolean wikibase, byte[] plus, byte[] minus) { // REF.MW: getDMSString
		if (step < 2) Set_coord();
		double deg = coord_deg;
		if (	dec < 0 
			&& (	(BryUtl.IsNotNullOrEmpty(plus) || BryUtl.IsNotNullOrEmpty(minus))
				||	wikibase	// NOTE: wikibase will always pass in empty plus / minus; still need to suppress "-" sign because letter has already been reversed; EX:"-2 E" -> "2 W" x> "-2 W" DATE:2017-04-02
				)
			) {
			deg = MathUtl.AbsAsDouble(deg);
		}
		tmp_bfr.AddDouble(deg).Add(Bry_deg);
		if (prec > 0) {
			if (!wikibase) // NOTE: do not add space if wikibase, else will fail in Module:en.w:WikidataCoord; PAGE:en.w:Hulme_Arch_Bridge DATE:2017-04-02
				tmp_bfr.AddByteSpace();
			tmp_bfr.AddDouble(coord_min).Add(wikibase ? Bry_apos_wb : Bry_apos_mw);
		}
		if (prec > 2) {
			if (!wikibase)	// NOTE: do not add space if wikibase, else will fail in Module:en.w:WikidataCoord; PAGE:en.w:Hulme_Arch_Bridge DATE:2017-04-02
				tmp_bfr.AddByteSpace();
			tmp_bfr.AddDouble(coord_sec).Add(wikibase ? Bry_quot_wb : Bry_quot_mw);
		}
		byte[] letter = null;
		if (dir_id == Dir_lat_id)
			letter = coord_dir_ns;
		if (dir_id == Dir_long_id)
			letter = coord_dir_ew;
		if (dec > 0 && BryUtl.IsNotNullOrEmpty(plus))
			letter = plus;
		if (dec < 0 && BryUtl.IsNotNullOrEmpty(minus))
			letter = minus;
		if (letter != null) {
			tmp_bfr.AddByteSpace();
			tmp_bfr.Add(letter);
		}
		return tmp_bfr.ToBryAndClear();
	}
	private void Parse_input(byte[] src) {	// REF.MW: toDec
		src = Parse_input_normalize(tmp_bfr, src);
		if (src == null) {error = -1; return;}
		int src_len = src.length;
		int word_idx = -1, word_bgn = 0, words_len = 0;
		int i = 0;			
		while (true) {
			boolean is_last = i == src_len;
			byte b = is_last ? AsciiByte.Space : src[i];
			switch (b) {
				case AsciiByte.Space:
					Parse_input_word(rv, src, ++word_idx, word_bgn, i);
					++words_len;
					i = BryFind.FindFwdWhileSpaceOrTab(src, i, src_len);
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
			case AsciiByte.Ltr_N:
			case AsciiByte.Ltr_E:
			case AsciiByte.Ltr_S:
			case AsciiByte.Ltr_W:
				return true;
			default:
				return false;
		}
	}
	private void Parse_input_word(double[] rv, byte[] input, int word_idx, int word_bgn, int word_end) {
		if (word_idx >= Input_units_len) return;
		byte unit_dlm = Input_units[word_idx];
		int pos = BryFind.FindFwd(input, unit_dlm, word_bgn, word_end);
		if (pos != BryFind.NotFound)	// remove dlms from end of bry; EX: "123'" -> "123"
			word_end = pos;
		if (!Parse_input_word_is_compass(input[word_bgn])) {	// if ( is_numeric( $v ) ) {
			double word_val = BryUtl.ToDoubleOr(input, word_bgn, word_end, DoubleUtl.NaN);
			if (!DoubleUtl.IsNaN(word_val)) {
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
					if (word_byte == AsciiByte.Ltr_N || word_byte == AsciiByte.Ltr_S) {error = -12; return;}
					break;
				case Dir_lat_id:
					if (word_byte == AsciiByte.Ltr_E || word_byte == AsciiByte.Ltr_W) {error = -12; return;}
					break;
				case Dir_unknown_id:
					if (word_byte == AsciiByte.Ltr_N || word_byte == AsciiByte.Ltr_S)	this.dir_id = Dir_lat_id;
					else																this.dir_id = Dir_long_id;
					break;
			}
			if (this.dir_id == Dir_lat_id) {
				double rv_0 = rv[0];
				if (rv_0 < -90 || rv_0 > 90) {error = -13; return;}
			}
			if (word_byte == AsciiByte.Ltr_S || word_byte == AsciiByte.Ltr_W)
				rv[3] = -1;
		}
	}
	private static byte Parse_dir(byte[] dir) {
		if (BryUtl.IsNullOrEmpty(dir)) return Dir_unknown_id;
		Object dir_obj = Dir_trie.MatchBgn(dir, 0, dir.length);
		return dir_obj == null ? Dir_unknown_id : ((ByteVal)dir_obj).Val();
	}
	private static int Parse_precision(int val) {	// REF.MW: MapSourcesMath.php|newCoord
		if		(val > -1 && val < 10)		return val;
		else if	(val == -1)					return 9;
		else								return 4;
	}
	private BryWtr tmp_bfr = BryWtr.NewAndReset(32);
	public static byte[] Parse_input_normalize(BryWtr bfr, byte[] src) {
		/*
		$w = str_replace( array( '‘', '’', '′' ), "'", $input );
		$w = str_replace( array( "''", '“', '”', '″' ), '"', $w );
		$w = str_replace( '−', '-', $w );
		$w = strtoupper( str_replace( array( '_', '/', "\t", "\n", "\r" ), ' ', $w ) );
		$w = str_replace( array( '°', "'", '"' ), array( '° ', "' ", '" ' ), $w );
		$w = trim( str_replace( array( 'N', 'S', 'E', 'W' ), array( ' N', ' S', ' E', ' W' ), $w ) );
		*/
		int src_end = src.length; if (src_end == 0) return null;			
		src = Trie__normalize__apos.Replace(bfr, src, 0, src_end);		// normalize apos separately, since 2 apos can go to quotes; EX: ‘’ -> "; PAGE:it.v:Morro_d'Oro DATE:2015-12-06
		src = Trie__normalize__rest.Replace(bfr, src, 0, src.length);	// normalize rest;
		return BryUtl.Trim(src);
	}
	private static final byte[]
	  Bry_deg = BryUtl.NewU8("°")
	, Bry_quot_mw = BryUtl.NewA7("&quot;")
	, Bry_quot_wb = BryUtl.NewA7("&#34;") // REF:en.w:Module:WikidataCoord
	, Bry_apos_mw = BryUtl.NewA7("'")
	, Bry_apos_wb = BryUtl.NewA7("&#39;") // REF:en.w:Module:WikidataCoord
	;
	private static final byte Dir_unknown_id = 0, Dir_lat_id = 1, Dir_long_id = 2;
	public static final byte[] Dir_lat_bry = BryUtl.NewA7("lat"), Dir_long_bry = BryUtl.NewA7("long");
	private static final Btrie_slim_mgr Dir_trie = Btrie_slim_mgr.ci_a7()	// NOTE:ci.ascii:MW_const.en
	.Add_bry_byte(Dir_lat_bry			, Dir_lat_id)
	.Add_bry_byte(Dir_long_bry			, Dir_long_id)
	;
	private static final byte[]
	  Compass_N = new byte[] {AsciiByte.Ltr_N}
	, Compass_E = new byte[] {AsciiByte.Ltr_E}
	, Compass_S = new byte[] {AsciiByte.Ltr_S}
	, Compass_W = new byte[] {AsciiByte.Ltr_W}
	;
	private static final byte Input_byte_degree = AsciiByte.Slash;	// NOTE: ugly cheat to avoid using multi-byte char; note that all "/" are swapped out to " ", so any remaining "/" was added by the normalizer; EX:  "123° 4/5" -> "123/ 4 5"
	private static final byte[] Input_units = new byte[] {Input_byte_degree, AsciiByte.Apos, AsciiByte.Quote, AsciiByte.Space};
	private static final int Input_units_len = Input_units.length;
	private static final Btrie_slim_mgr Trie__normalize__apos = Btrie_slim_mgr.cs()
	.Add_replace_many	(AsciiByte.AposBry, "‘", "’", "′");
	private static final Btrie_slim_mgr Trie__normalize__rest = Btrie_slim_mgr.cs()
	.Add_replace_many	("' "					, "'")
	.Add_replace_many	("\" "					, "\"", "''", "“", "”", "″")
	.Add_replace_many	(AsciiByte.DashBry, "-", "−")							// NOTE: emdash and endash
	.Add_replace_many	(AsciiByte.SpaceBry, " ", "_", "/", "\t", "\n", "\r") 	// NOTE: " " = &nbsp;
	.Add_replace_many	("/ "					, "°")
	.Add_replace_many	(" N"					, "N", "n")
	.Add_replace_many	(" S"					, "S", "s")
	.Add_replace_many	(" E"					, "E", "e")
	.Add_replace_many	(" W"					, "W", "w");
	public static final Map_math Instance = new Map_math();
}
