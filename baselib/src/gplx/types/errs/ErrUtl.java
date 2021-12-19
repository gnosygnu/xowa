/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.errs;
import gplx.types.basics.utls.ClassLni;
import gplx.types.basics.utls.ErrLni;
import gplx.types.basics.utls.ObjectLni;
import gplx.types.basics.utls.StringFormatLni;
public class ErrUtl extends ErrLni {
	public static Err NewFmt(String fmt, Object... args)                  {return new Err("GENERAL", StringFormatLni.Format(fmt, args));}
	public static Err NewFmt(Exception e, String fmt, Object... args)     {return new Err("GENERAL", StringFormatLni.Format(fmt, args)).InnerSet(e);}
	public static Err NewArgs(String fmt, Object... args)                 {return new Err("GENERAL", fmt).ArgsAddAry(args);}
	public static Err NewArgs(Exception e, String fmt, Object... args)    {return new Err("GENERAL", fmt).InnerSet(e).ArgsAddAry(args);}
	public static Err NewUnimplemented()                                  {return new Err("UNIMPLEMENTED", "method unimplemented");}
	public static Err NewUnimplemented(String msg, Object... args)        {return new Err("UNIMPLEMENTED", "method unimplemented").ArgsAddAry(args);}
	public static Err NewUnsupported()                                    {return new Err("UNSUPPORTED"  , "method unsupported");}
	public static Err NewUnhandled(Object o)                              {return new Err("UNHANDLED", "value is not handled").ArgsAdd("val", o);}
	public static Err NewUnhandled(Object o, String msg)                  {return new Err("UNHANDLED", "value is not handled").ArgsAdd("val", o).ArgsAdd("msg", msg);}
	public static Err NewCast(Class<?> t, Object o)                       {return NewCast(null, t, o);}
	public static Err NewCast(Exception e, Class<?> t, Object o)          {return new Err("TYPE_MISMATCH", "cast failed").InnerSet(e).ArgsAdd("expdType", ClassLni.CanonicalName(t)).ArgsAdd("actlType", ClassLni.NameByObj(o)).ArgsAdd("actlObj", ObjectLni.ToStrOrNullMark(o));}
	public static Err NewNull()                                           {return new Err("NULL", "object cannot be null");}
	public static Err NewNull(String name)                                {return new Err("NULL", "object cannot be null").ArgsAdd("name", name);}
	public static Err NewParse(String c, String raw)                      {return NewParse(raw, null, c);}
	public static Err NewParse(Class<?> c, String raw)                    {return NewParse(raw, null, ClassLni.CanonicalName(c));}
	public static Err NewParse(Exception e, Class<?> c, String raw)       {return NewParse(raw, e, ClassLni.CanonicalName(c));}
	private static Err NewParse(String raw, Exception e, String c)        {return new Err("PARSE", "parse failed").InnerSet(e).ArgsAdd("cls", c).ArgsAdd("raw", raw);}
	public static Err NewDeprecated(String s)                             {return new Err("DEPRECATED", "deprecated").ArgsAdd("method", s);}
	public static Err NewCanceled()                                       {return new Err("OP_CANCELED", "operation canceled");}
	public static Err NewMissingIdx(int idx, int len)                     {return new Err("MISSING_IDX", "index out of bounds").ArgsAdd("idx", idx).ArgsAdd("len", len);}
	public static Err NewMissingKey(String key)                           {return new Err("MISSING_KEY", "key not found").ArgsAdd("key", key);}
	public static Err NewInvalidOp(String msg)                            {return new Err("INVALID_OP" , "operation invalid").ArgsAdd("msg", msg);}
	public static Err NewInvalidArg(Object... args)                       {return new Err("INVALID_ARG", "arg invalid").ArgsAddAry(args);}

	public static String ToStrFull(Throwable e) {return CastOrWrap(e).ToStrFull();}
	public static String ToStrLog (Throwable e) {return CastOrWrap(e).ToStrLog();}
	public static Err CastOrWrap(Throwable e) {return ClassLni.EqByObj(Err.class, e) ? (Err)e : new Err("WRAPPED", ErrLni.Message(e));}
}
