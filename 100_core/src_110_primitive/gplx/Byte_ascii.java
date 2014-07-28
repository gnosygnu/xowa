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
public class Byte_ascii {
	public static final byte
	      Nil			=   0												 , Backfeed         =   8, Tab				=   9
		, NewLine		=  10, Formfeed			=  12, CarriageReturn	=  13
													 , Space			=  32, Bang				=  33, Quote			=  34
		, Hash			=  35, Dollar           =  36, Percent			=  37, Amp				=  38, Apos				=  39
		, Paren_bgn		=  40, Paren_end		=  41, Asterisk			=  42, Plus				=  43, Comma			=  44
		, Dash			=  45, Dot				=  46, Slash			=  47, Num_0			=  48, Num_1			=  49
		, Num_2			=  50, Num_3			=  51, Num_4			=  52, Num_5			=  53, Num_6			=  54
		, Num_7			=  55, Num_8			=  56, Num_9			=  57, Colon			=  58, Semic			=  59
		, Lt			=  60, Eq				=  61, Gt				=  62, Question			=  63, At				=  64
		, Ltr_A			=  65, Ltr_B			=  66, Ltr_C			=  67, Ltr_D			=  68, Ltr_E			=  69
		, Ltr_F			=  70, Ltr_G			=  71, Ltr_H			=  72, Ltr_I			=  73, Ltr_J			=  74
		, Ltr_K			=  75, Ltr_L			=  76, Ltr_M			=  77, Ltr_N			=  78, Ltr_O			=  79
		, Ltr_P			=  80, Ltr_Q			=  81, Ltr_R			=  82, Ltr_S			=  83, Ltr_T			=  84
		, Ltr_U			=  85, Ltr_V			=  86, Ltr_W			=  87, Ltr_X			=  88, Ltr_Y			=  89
		, Ltr_Z			=  90, Brack_bgn		=  91, Backslash		=  92, Brack_end		=  93, Pow				=  94	// Circumflex             
		, Underline     =  95, Tick				=  96, Ltr_a			=  97, Ltr_b            =  98, Ltr_c			=  99
		, Ltr_d			= 100, Ltr_e			= 101, Ltr_f			= 102, Ltr_g			= 103, Ltr_h			= 104
		, Ltr_i			= 105, Ltr_j			= 106, Ltr_k			= 107, Ltr_l			= 108, Ltr_m			= 109
		, Ltr_n			= 110, Ltr_o			= 111, Ltr_p			= 112, Ltr_q			= 113, Ltr_r			= 114
		, Ltr_s			= 115, Ltr_t			= 116, Ltr_u			= 117, Ltr_v			= 118, Ltr_w			= 119
		, Ltr_x			= 120, Ltr_y			= 121, Ltr_z			= 122, Curly_bgn		= 123, Pipe				= 124
		, Curly_end		= 125, Tilde			= 126
		;
	public static final byte Max_7_bit = (byte)127, Ascii_min = 0, Ascii_max = 127;
	public static final byte[] Space_len2 = new byte[] {Space, Space}, Space_len4 = new byte[] {Space, Space, Space, Space};
	public static boolean Is_ltr(byte b) {
		return (	b >= Byte_ascii.Ltr_a && b <= Byte_ascii.Ltr_z
				||	b >= Byte_ascii.Ltr_A && b <= Byte_ascii.Ltr_Z);
	}
	public static boolean Is_ws(byte b) {
		switch (b) {
			case Byte_ascii.Tab: case Byte_ascii.NewLine: case Byte_ascii.CarriageReturn: case Byte_ascii.Space: return true;
			default: return false;
		}
	}
	public static boolean Is_num(byte b) {
		return b > Byte_ascii.Slash && b < Byte_ascii.Colon;
	}
	public static int Xto_digit(byte b) {return b - Byte_ascii.Num_0;}
	public static byte Case_upper(byte b) {
		return b > 96 && b < 123
			? (byte)(b - 32)
			: b
		  ;
	}
	public static byte Case_lower(byte b) {
		return b > 64 && b < 91
			? (byte)(b + 32)
			: b
		  ;
	}
	public static final byte[]
	  Dot_bry				= new byte[] {Byte_ascii.Dot}
	, NewLine_bry			= new byte[] {Byte_ascii.NewLine}
	, Colon_bry				= new byte[] {Byte_ascii.Colon}
	, Lt_bry				= new byte[] {Byte_ascii.Lt}
	, Gt_bry				= new byte[] {Byte_ascii.Gt}
	, Brack_bgn_bry			= new byte[] {Byte_ascii.Brack_bgn}
	, Brack_end_bry			= new byte[] {Byte_ascii.Brack_end}
	, Apos_bry				= new byte[] {Byte_ascii.Apos}
	, Quote_bry				= new byte[] {Byte_ascii.Quote}
	, Pipe_bry				= new byte[] {Byte_ascii.Pipe}
	, Underline_bry			= new byte[] {Byte_ascii.Underline}
	, Asterisk_bry			= new byte[] {Byte_ascii.Asterisk}
	, Space_bry				= new byte[] {Byte_ascii.Space}
	;
}
