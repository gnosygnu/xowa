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
package gplx.gfml; import gplx.*;
import org.junit.*; import gplx.core.strings.*; import gplx.core.envs.*;
public class z901_perf_tst {
	TimerWatch tmr = TimerWatch.new_();
	@Test  public void EmptyTestSoJunitWillNotFail() {}
//		@Test 
	public void Long() {
//			String longText = String_.Repeat("a", 30 * 1000 * 1000);
		String longText = Io_mgr.Instance.LoadFilStr(Io_url_.new_any_("C:\\core_weekly.temp.gfio"));
//			String_bldr sbXml = String_bldr_.new_();
//			sbXml.Add("<");
//			sbXml.Add(longText);
//			sbXml.Add("/>");
//			tmr.Bgn();
//			gplx.langs.xmls.XmlDoc_.parse(sbXml.To_str());
//			tmr.End_and_print("xml");	// 400

		String_bldr sbGfml = String_bldr_.new_();
		for (int i = 0; i < 10; i++)  {
		sbGfml.Add(longText);
//			sbGfml.Add(longText);
//			sbGfml.Add(longText);
//			sbGfml.Add(longText);
		}
//			tmr.Bgn();
//			gplx.core.texts.CharStream.pos0_(sbGfml.To_str());
//			tmr.End_and_print("char");	// 1700

		tmr.Bgn();
		GfmlDoc_.parse_any_eol_(sbGfml.To_str());
		tmr.End_and_print("gfml");	// 1700
	}
	//@Test  
	public void Prop() {
		int max = 100 * 1000 * 1000;
		PerfFieldVsProc c = new PerfFieldVsProc();
		int v = 0;
		tmr.Bgn();
		for (int i = 0; i < max; i++)
			v = member;
		tmr.End_and_print("member");
		tmr.Bgn();
		for (int i = 0; i < max; i++)
			v = c.Val_field;
		tmr.End_and_print("field");
		tmr.Bgn();
		for (int i = 0; i < max; i++)
			v = c.Val_proc();
		tmr.End_and_print("proc");
            Tfds.Write(v);
	}
//		@Test  
//		public void ClassComp() {
//			int max = 100 * 1000 * 1000;
//			ClassType ct = new ClassType3();
//			long v = 0;
//			//   2625 CS, 718 JAVA
//			tmr.Bgn();
//			for (int i = 0; i < max; i++) {
//				if		(ct.Type() == ClassType_.Type_1) v += 1;
//				else if (ct.Type() == ClassType_.Type_2) v += 2;
//				else if (ct.Type() == ClassType_.Type_3) v += 3;
//			}
//			tmr.End_and_print("var");
//			v = 0;
//			// 12437 CS, 578 JAVA
//			tmr.Bgn();
//			for (int i = 0; i < max; i++) {
//				Class<?> type = ct.getClass();
//				if		(type == typeof(ClassType1)) v += 1;
//				else if (type == typeof(ClassType2)) v += 2;
//				else if (type == typeof(ClassType3)) v += 3;
//			}
//			tmr.End_and_print("type");
//		}
	interface ClassType {int Type();}
	class ClassType_ {public static final    int Type_1 = 1, Type_2 = 2, Type_3 = 3;}
	class ClassType1 implements ClassType {public int Type() {return ClassType_.Type_1;}}
	class ClassType2 implements ClassType {public int Type() {return ClassType_.Type_2;}}
	class ClassType3 implements ClassType {public int Type() {return ClassType_.Type_3;}}
	int member = 1;
}
class PerfFieldVsProc {
	public int Val_field = 1;
	public int Val_proc() {return 1;}
}
class TimerWatch {
	public void Bgn() {bgnTime = System_.Ticks();} long bgnTime;
	public void End() {duration = System_.Ticks() - bgnTime;} long duration;
	public void End_and_print(String text) {
		this.End();
		Tfds.Write(XtoStr_ms() + " " + text);
	}
	public String XtoStr_ms() {return Long_.To_str(duration);}
        public static TimerWatch new_() {
		TimerWatch rv = new TimerWatch();
		rv.Bgn();
		return rv;
	}	TimerWatch() {}
}	
