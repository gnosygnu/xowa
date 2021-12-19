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
package gplx.xowa.xtns.scribunto.errs;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDecimal;
import gplx.libs.files.Io_url;
public interface Gfo_comp_op_1 {
	boolean Comp_bool(boolean val, boolean comp);
	boolean Comp_byte(byte val, byte comp);
	boolean Comp_int(int val, int comp);
	boolean Comp_long(long val, long comp);
	boolean Comp_float(float val, float comp);
	boolean Comp_double(double val, double comp);
	boolean Comp_decimal(GfoDecimal val, GfoDecimal comp);
	boolean Comp_char(char val, char comp);
	boolean Comp_str(String val, String comp);
	boolean Comp_bry(byte[] val, byte[] comp);
	boolean Comp_date(GfoDate val, GfoDate comp);
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
	public boolean Comp_decimal(GfoDecimal val, GfoDecimal comp)			{return val.Eq(comp);}
	public boolean Comp_char(char val, char comp)							{return val == comp;}
	public boolean Comp_str(String val, String comp)						{return StringUtl.Eq(val, comp);}
	public boolean Comp_bry(byte[] val, byte[] comp)						{return BryLni.Eq(val, comp);}
	public boolean Comp_date(GfoDate val, GfoDate comp)					{return val.Eq(comp);}
	public boolean Comp_url(Io_url val, Io_url comp)						{return val.Eq(comp);}
	public boolean Comp_val(Gfo_val val, Gfo_val comp)						{return val.Eq(comp);}
	public boolean Comp_obj(Object val, Object comp)						{return ObjectUtl.Eq(val, comp);}
}
class Gfo_comp_op_eqn implements Gfo_comp_op_1 {
	public boolean Comp_bool(boolean val, boolean comp)							{return val != comp;}
	public boolean Comp_byte(byte val, byte comp)							{return val != comp;}
	public boolean Comp_int(int val, int comp)								{return val != comp;}
	public boolean Comp_long(long val, long comp)							{return val != comp;}
	public boolean Comp_float(float val, float comp)						{return val != comp;}
	public boolean Comp_double(double val, double comp)					{return val != comp;}
	public boolean Comp_decimal(GfoDecimal val, GfoDecimal comp)			{return !val.Eq(comp);}
	public boolean Comp_char(char val, char comp)							{return val != comp;}
	public boolean Comp_str(String val, String comp)						{return !StringUtl.Eq(val, comp);}
	public boolean Comp_bry(byte[] val, byte[] comp)						{return !BryLni.Eq(val, comp);}
	public boolean Comp_date(GfoDate val, GfoDate comp)					{return !val.Eq(comp);}
	public boolean Comp_url(Io_url val, Io_url comp)						{return !val.Eq(comp);}
	public boolean Comp_val(Gfo_val val, Gfo_val comp)						{return !val.Eq(comp);}
	public boolean Comp_obj(Object val, Object comp)						{return ObjectUtl.Eq(val, comp);}
}
class Gfo_comp_op_lt implements Gfo_comp_op_1 {
	public boolean Comp_bool(boolean val, boolean comp)							{return !val && comp;}	// false < true
	public boolean Comp_byte(byte val, byte comp)							{return val < comp;}
	public boolean Comp_int(int val, int comp)								{return val < comp;}
	public boolean Comp_long(long val, long comp)							{return val < comp;}
	public boolean Comp_float(float val, float comp)						{return val < comp;}
	public boolean Comp_double(double val, double comp)					{return val < comp;}
	public boolean Comp_decimal(GfoDecimal val, GfoDecimal comp)			{return val.CompLt(comp);}
	public boolean Comp_char(char val, char comp)							{return val < comp;}
	public boolean Comp_str(String val, String comp)						{return StringUtl.Compare(val, comp) == CompareAbleUtl.Same;}
	public boolean Comp_bry(byte[] val, byte[] comp)						{return BryUtl.Compare(val, comp) < CompareAbleUtl.Same;}
	public boolean Comp_date(GfoDate val, GfoDate comp)					{return val.compareTo(comp) < CompareAbleUtl.Same;}
	public boolean Comp_url(Io_url val, Io_url comp)						{return val.compareTo(comp) < CompareAbleUtl.Same;}
	public boolean Comp_val(Gfo_val val, Gfo_val comp)						{return val.compareTo(comp) < CompareAbleUtl.Same;}
	public boolean Comp_obj(Object val, Object comp)						{return CompareAbleUtl.Compare_obj(val, comp) < CompareAbleUtl.Same;}
}
class Gfo_comp_op_lte implements Gfo_comp_op_1 {
	public boolean Comp_bool(boolean val, boolean comp)							{return !(val && !comp);}	// !(true && false)
	public boolean Comp_byte(byte val, byte comp)							{return val <= comp;}
	public boolean Comp_int(int val, int comp)								{return val <= comp;}
	public boolean Comp_long(long val, long comp)							{return val <= comp;}
	public boolean Comp_float(float val, float comp)						{return val <= comp;}
	public boolean Comp_double(double val, double comp)					{return val <= comp;}
	public boolean Comp_decimal(GfoDecimal val, GfoDecimal comp)			{return val.CompLte(comp);}
	public boolean Comp_char(char val, char comp)							{return val <= comp;}
	public boolean Comp_str(String val, String comp)						{return StringUtl.Compare(val, comp) != CompareAbleUtl.More;}
	public boolean Comp_bry(byte[] val, byte[] comp)						{return BryUtl.Compare(val, comp) <= CompareAbleUtl.Same;}
	public boolean Comp_date(GfoDate val, GfoDate comp)					{return val.compareTo(comp) <= CompareAbleUtl.Same;}
	public boolean Comp_url(Io_url val, Io_url comp)						{return val.compareTo(comp) <= CompareAbleUtl.Same;}
	public boolean Comp_val(Gfo_val val, Gfo_val comp)						{return val.compareTo(comp) <= CompareAbleUtl.Same;}
	public boolean Comp_obj(Object val, Object comp)						{return CompareAbleUtl.Compare_obj(val, comp) <= CompareAbleUtl.Same;}
}
class Gfo_comp_op_mt implements Gfo_comp_op_1 {
	public boolean Comp_bool(boolean val, boolean comp)							{return val && !comp;}	// true > false
	public boolean Comp_byte(byte val, byte comp)							{return val > comp;}
	public boolean Comp_int(int val, int comp)								{return val > comp;}
	public boolean Comp_long(long val, long comp)							{return val > comp;}
	public boolean Comp_float(float val, float comp)						{return val > comp;}
	public boolean Comp_double(double val, double comp)					{return val > comp;}
	public boolean Comp_decimal(GfoDecimal val, GfoDecimal comp)			{return val.CompLt(comp);}
	public boolean Comp_char(char val, char comp)							{return val > comp;}
	public boolean Comp_str(String val, String comp)						{return StringUtl.Compare(val, comp) == CompareAbleUtl.More;}
	public boolean Comp_bry(byte[] val, byte[] comp)						{return BryUtl.Compare(val, comp) > CompareAbleUtl.Same;}
	public boolean Comp_date(GfoDate val, GfoDate comp)					{return val.compareTo(comp) > CompareAbleUtl.Same;}
	public boolean Comp_url(Io_url val, Io_url comp)						{return val.compareTo(comp) > CompareAbleUtl.Same;}
	public boolean Comp_val(Gfo_val val, Gfo_val comp)						{return val.compareTo(comp) > CompareAbleUtl.Same;}
	public boolean Comp_obj(Object val, Object comp)						{return CompareAbleUtl.Compare_obj(val, comp) > CompareAbleUtl.Same;}
}
class Gfo_comp_op_mte implements Gfo_comp_op_1 {
	public boolean Comp_bool(boolean val, boolean comp)							{return !(!val && comp);}	// !(false && true)
	public boolean Comp_byte(byte val, byte comp)							{return val >= comp;}
	public boolean Comp_int(int val, int comp)								{return val >= comp;}
	public boolean Comp_long(long val, long comp)							{return val >= comp;}
	public boolean Comp_float(float val, float comp)						{return val >= comp;}
	public boolean Comp_double(double val, double comp)					{return val >= comp;}
	public boolean Comp_decimal(GfoDecimal val, GfoDecimal comp)			{return val.CompLte(comp);}
	public boolean Comp_char(char val, char comp)							{return val >= comp;}
	public boolean Comp_str(String val, String comp)						{return StringUtl.Compare(val, comp) != CompareAbleUtl.Less;}
	public boolean Comp_bry(byte[] val, byte[] comp)						{return BryUtl.Compare(val, comp) >= CompareAbleUtl.Same;}
	public boolean Comp_date(GfoDate val, GfoDate comp)					{return val.compareTo(comp) >= CompareAbleUtl.Same;}
	public boolean Comp_url(Io_url val, Io_url comp)						{return val.compareTo(comp) >= CompareAbleUtl.Same;}
	public boolean Comp_val(Gfo_val val, Gfo_val comp)						{return val.compareTo(comp) >= CompareAbleUtl.Same;}
	public boolean Comp_obj(Object val, Object comp)						{return CompareAbleUtl.Compare_obj(val, comp) >= CompareAbleUtl.Same;}
}
