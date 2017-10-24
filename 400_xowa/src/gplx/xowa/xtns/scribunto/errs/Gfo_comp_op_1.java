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
package gplx.xowa.xtns.scribunto.errs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
public interface Gfo_comp_op_1 {
	boolean Comp_bool(boolean val, boolean comp);
	boolean Comp_byte(byte val, byte comp);
	boolean Comp_int(int val, int comp);
	boolean Comp_long(long val, long comp);
	boolean Comp_float(float val, float comp);
	boolean Comp_double(double val, double comp);
	boolean Comp_decimal(Decimal_adp val, Decimal_adp comp);
	boolean Comp_char(char val, char comp);
	boolean Comp_str(String val, String comp);
	boolean Comp_bry(byte[] val, byte[] comp);
	boolean Comp_date(DateAdp val, DateAdp comp);
	boolean Comp_url(Io_url val, Io_url comp);
	boolean Comp_val(Gfo_val val, Gfo_val comp);
	boolean Comp_obj(Object val, Object comp);
}
class Gfo_comp_op_eq implements Gfo_comp_op_1 {
	public boolean Comp_bool(boolean val, boolean comp)							{return val == comp;}
	public boolean Comp_byte(byte val, byte comp)							{return val == comp;}
	public boolean Comp_int(int val, int comp)								{return val == comp;}
	public boolean Comp_long(long val, long comp)							{return val == comp;}
	public boolean Comp_float(float val, float comp)						{return val == comp;}
	public boolean Comp_double(double val, double comp)					{return val == comp;}
	public boolean Comp_decimal(Decimal_adp val, Decimal_adp comp)			{return val.Eq(comp);}
	public boolean Comp_char(char val, char comp)							{return val == comp;}
	public boolean Comp_str(String val, String comp)						{return String_.Eq(val, comp);}
	public boolean Comp_bry(byte[] val, byte[] comp)						{return Bry_.Eq(val, comp);}
	public boolean Comp_date(DateAdp val, DateAdp comp)					{return val.Eq(comp);}
	public boolean Comp_url(Io_url val, Io_url comp)						{return val.Eq(comp);}
	public boolean Comp_val(Gfo_val val, Gfo_val comp)						{return val.Eq(comp);}
	public boolean Comp_obj(Object val, Object comp)						{return Object_.Eq(val, comp);}
}
class Gfo_comp_op_eqn implements Gfo_comp_op_1 {
	public boolean Comp_bool(boolean val, boolean comp)							{return val != comp;}
	public boolean Comp_byte(byte val, byte comp)							{return val != comp;}
	public boolean Comp_int(int val, int comp)								{return val != comp;}
	public boolean Comp_long(long val, long comp)							{return val != comp;}
	public boolean Comp_float(float val, float comp)						{return val != comp;}
	public boolean Comp_double(double val, double comp)					{return val != comp;}
	public boolean Comp_decimal(Decimal_adp val, Decimal_adp comp)			{return !val.Eq(comp);}
	public boolean Comp_char(char val, char comp)							{return val != comp;}
	public boolean Comp_str(String val, String comp)						{return !String_.Eq(val, comp);}
	public boolean Comp_bry(byte[] val, byte[] comp)						{return !Bry_.Eq(val, comp);}
	public boolean Comp_date(DateAdp val, DateAdp comp)					{return !val.Eq(comp);}
	public boolean Comp_url(Io_url val, Io_url comp)						{return !val.Eq(comp);}
	public boolean Comp_val(Gfo_val val, Gfo_val comp)						{return !val.Eq(comp);}
	public boolean Comp_obj(Object val, Object comp)						{return Object_.Eq(val, comp);}
}
class Gfo_comp_op_lt implements Gfo_comp_op_1 {
	public boolean Comp_bool(boolean val, boolean comp)							{return !val && comp;}	// false < true
	public boolean Comp_byte(byte val, byte comp)							{return val < comp;}
	public boolean Comp_int(int val, int comp)								{return val < comp;}
	public boolean Comp_long(long val, long comp)							{return val < comp;}
	public boolean Comp_float(float val, float comp)						{return val < comp;}
	public boolean Comp_double(double val, double comp)					{return val < comp;}
	public boolean Comp_decimal(Decimal_adp val, Decimal_adp comp)			{return val.Comp_lt(comp);}
	public boolean Comp_char(char val, char comp)							{return val < comp;}
	public boolean Comp_str(String val, String comp)						{return String_.Compare(val, comp) == CompareAble_.Same;}
	public boolean Comp_bry(byte[] val, byte[] comp)						{return Bry_.Compare(val, comp) < CompareAble_.Same;}
	public boolean Comp_date(DateAdp val, DateAdp comp)					{return val.compareTo(comp) < CompareAble_.Same;}
	public boolean Comp_url(Io_url val, Io_url comp)						{return val.compareTo(comp) < CompareAble_.Same;}
	public boolean Comp_val(Gfo_val val, Gfo_val comp)						{return val.compareTo(comp) < CompareAble_.Same;}
	public boolean Comp_obj(Object val, Object comp)						{return CompareAble_.Compare_obj(val, comp) < CompareAble_.Same;}
}
class Gfo_comp_op_lte implements Gfo_comp_op_1 {
	public boolean Comp_bool(boolean val, boolean comp)							{return !(val && !comp);}	// !(true && false)
	public boolean Comp_byte(byte val, byte comp)							{return val <= comp;}
	public boolean Comp_int(int val, int comp)								{return val <= comp;}
	public boolean Comp_long(long val, long comp)							{return val <= comp;}
	public boolean Comp_float(float val, float comp)						{return val <= comp;}
	public boolean Comp_double(double val, double comp)					{return val <= comp;}
	public boolean Comp_decimal(Decimal_adp val, Decimal_adp comp)			{return val.Comp_lte(comp);}
	public boolean Comp_char(char val, char comp)							{return val <= comp;}
	public boolean Comp_str(String val, String comp)						{return String_.Compare(val, comp) != CompareAble_.More;}
	public boolean Comp_bry(byte[] val, byte[] comp)						{return Bry_.Compare(val, comp) <= CompareAble_.Same;}
	public boolean Comp_date(DateAdp val, DateAdp comp)					{return val.compareTo(comp) <= CompareAble_.Same;}
	public boolean Comp_url(Io_url val, Io_url comp)						{return val.compareTo(comp) <= CompareAble_.Same;}
	public boolean Comp_val(Gfo_val val, Gfo_val comp)						{return val.compareTo(comp) <= CompareAble_.Same;}
	public boolean Comp_obj(Object val, Object comp)						{return CompareAble_.Compare_obj(val, comp) <= CompareAble_.Same;}
}
class Gfo_comp_op_mt implements Gfo_comp_op_1 {
	public boolean Comp_bool(boolean val, boolean comp)							{return val && !comp;}	// true > false
	public boolean Comp_byte(byte val, byte comp)							{return val > comp;}
	public boolean Comp_int(int val, int comp)								{return val > comp;}
	public boolean Comp_long(long val, long comp)							{return val > comp;}
	public boolean Comp_float(float val, float comp)						{return val > comp;}
	public boolean Comp_double(double val, double comp)					{return val > comp;}
	public boolean Comp_decimal(Decimal_adp val, Decimal_adp comp)			{return val.Comp_lt(comp);}
	public boolean Comp_char(char val, char comp)							{return val > comp;}
	public boolean Comp_str(String val, String comp)						{return String_.Compare(val, comp) == CompareAble_.More;}
	public boolean Comp_bry(byte[] val, byte[] comp)						{return Bry_.Compare(val, comp) > CompareAble_.Same;}
	public boolean Comp_date(DateAdp val, DateAdp comp)					{return val.compareTo(comp) > CompareAble_.Same;}
	public boolean Comp_url(Io_url val, Io_url comp)						{return val.compareTo(comp) > CompareAble_.Same;}
	public boolean Comp_val(Gfo_val val, Gfo_val comp)						{return val.compareTo(comp) > CompareAble_.Same;}
	public boolean Comp_obj(Object val, Object comp)						{return CompareAble_.Compare_obj(val, comp) > CompareAble_.Same;}
}
class Gfo_comp_op_mte implements Gfo_comp_op_1 {
	public boolean Comp_bool(boolean val, boolean comp)							{return !(!val && comp);}	// !(false && true)
	public boolean Comp_byte(byte val, byte comp)							{return val >= comp;}
	public boolean Comp_int(int val, int comp)								{return val >= comp;}
	public boolean Comp_long(long val, long comp)							{return val >= comp;}
	public boolean Comp_float(float val, float comp)						{return val >= comp;}
	public boolean Comp_double(double val, double comp)					{return val >= comp;}
	public boolean Comp_decimal(Decimal_adp val, Decimal_adp comp)			{return val.Comp_lte(comp);}
	public boolean Comp_char(char val, char comp)							{return val >= comp;}
	public boolean Comp_str(String val, String comp)						{return String_.Compare(val, comp) != CompareAble_.Less;}
	public boolean Comp_bry(byte[] val, byte[] comp)						{return Bry_.Compare(val, comp) >= CompareAble_.Same;}
	public boolean Comp_date(DateAdp val, DateAdp comp)					{return val.compareTo(comp) >= CompareAble_.Same;}
	public boolean Comp_url(Io_url val, Io_url comp)						{return val.compareTo(comp) >= CompareAble_.Same;}
	public boolean Comp_val(Gfo_val val, Gfo_val comp)						{return val.compareTo(comp) >= CompareAble_.Same;}
	public boolean Comp_obj(Object val, Object comp)						{return CompareAble_.Compare_obj(val, comp) >= CompareAble_.Same;}
}
