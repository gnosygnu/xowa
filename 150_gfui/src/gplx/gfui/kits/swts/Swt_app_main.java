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
package gplx.gfui.kits.swts; import gplx.*; import gplx.gfui.*; import gplx.gfui.kits.*;
import gplx.Byte_ascii;
import gplx.String_;

import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
public class Swt_app_main {
	public static void main(String[] args) {
//		Drag_drop();
//		List_fonts();
//		keystrokes(args);
		Permission_denied();
//		Combo_default();
//		Combo_composite();
	}
	static void Drag_drop() {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());
		final CTabFolder folder = new CTabFolder(shell, SWT.BORDER);
		folder.setLayoutData(new GridData(GridData.FILL_BOTH));
		for (int i = 0; i < 10; i++) {
			CTabItem item = new CTabItem(folder, SWT.NONE);
			item.setText("item "+i);
			Text text = new Text(folder, SWT.BORDER | SWT.MULTI | SWT.VERTICAL);
			text.setText("Text control for "+i);
			item.setControl(text);
			if (i == 9) {
				item.setShowClose(false);
				item.setText("+");
//				item.setImage(new Image(Display.getDefault(), "J:\\gplx\\xowa\\user\\anonymous\\app\\img\\edit\\format-bold-A.png"));
			}
		}
		ToolBar t = new ToolBar( folder, SWT.FLAT );
		ToolItem i = new ToolItem( t, SWT.PUSH );
		i.setText( "add" );
		folder.setTopRight( t, SWT.RIGHT );
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
    static void keystrokes(String[] args) 
    {

        Display display = new Display ();

        final Shell shell = new Shell (display);

//        display.addFilter(SWT.KeyDown, new Listener() {
//
//            public void handleEvent(Event e) {
//                if(((e.stateMask & SWT.CTRL) == SWT.CTRL) && (e.keyCode == 'f'))
//                {
//                    System.out.println("From Display I am the Key down !!" + e.keyCode);
//                }
//            }
//        });
        shell.addKeyListener(new KeyListener() {
            public void keyReleased(KeyEvent e) {
//                if(((e.stateMask & SWT.CTRL) == SWT.CTRL) && (e.keyCode == 'f'))
//                {
//                    shell.setBackground(orig);
//                    System.out.println("Key up !!");
//                }
            	System.out.println(e.stateMask + " " + e.keyCode);
            }
            public void keyPressed(KeyEvent e) {
//            	System.out.println(e.stateMask + " " + e.keyCode);
            }
        });
        shell.addMouseListener(new MouseListener() {			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0.button);
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

        shell.setSize (200, 200);
        shell.open ();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch ()) display.sleep ();
        }
        display.dispose ();

    }
	static void List_fonts() {
		java.awt.GraphicsEnvironment e = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
		java.awt.Font[] fonts = e.getAllFonts(); // Get the fonts
		for (java.awt.Font f : fonts) {
		  System.out.println(f.getFontName());
		}		
	}
	static void Permission_denied() {
		String html
			= "<html>\n"
			+ "<head>\n" 
			+ "<script>\n"
			+ "  function permissionDeniedExample() {\n"
			+ "    var sel = window.getSelection();\n" 
			+ "    alert('calling sel.rangeCount');\n"
			+ "    alert('sel.rangeCount = ' + sel.rangeCount);\n"
			+ "  }\n"
			+ "</script>\n"
			+ "</head>\n"
			+ "<body>\n" 
			+ "  <a href='#direct_call_fails'/>click to call permissionDeniedExample -> will throw error and not show sel.rangeCount</a><br/>\n"
			+ "  <a href='#wrapped_call_works'/>click to call permissionDeniedExample inside a setTimeout -> will show sel.rangeCount</a><br/>\n"
			+ "</body>\n"
			+ "</html>\n"
			;

		System.setProperty
		( "org.eclipse.swt.browser.XULRunnerPath"
		// ADJUST THIS PATH AS NECESSARY ON YOUR MACHINE
		, "C:\\xowa\\bin\\windows\\xulrunner"
		);
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		final Browser browser;
		try {
			browser = new Browser(shell, SWT.MOZILLA);	// changed from none
			browser.addLocationListener(new LocationListener() {				
				@Override
				public void changing(LocationEvent arg0) {
					if (arg0.location.equals("about:blank")) return;
					arg0.doit = false;
				}
				
				@Override
				public void changed(LocationEvent arg0) {
					String location = arg0.location;
					if (location.equals("about:blank")) return;
					
					// build code
					String code = "alert('unknown_link:" + location + "')";
					if 		(location.contains("direct_call_fails"))
						code = "permissionDeniedExample();";
					else if (location.contains("wrapped_call_works"))
						code = "setTimeout(function(){permissionDeniedExample();}, 1);";
					
					// evaluate code
					try {
						browser.evaluate(code);
					} catch (Exception e) {
						System.out.println(e);
					}
					arg0.doit = false;
				}
			});
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			display.dispose();
			return;
		}
		browser.setText(html);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	public static void Combo_dflt() {
	    Display display = new Display();
	    Shell shell = new Shell(display);
	    shell.setLayout(new FillLayout());
	
	    String[] ITEMS = { "A", "B", "C", "D" };
	
	    final Combo combo = new Combo(shell, SWT.DROP_DOWN);
	    combo.setItems(ITEMS);
	    combo.select(2);
	    
	    combo.addSelectionListener(new SelectionListener() {
	      public void widgetSelected(SelectionEvent e) {
	        System.out.println(combo.getText());
	      }
	
	      public void widgetDefaultSelected(SelectionEvent e) {
	        System.out.println(combo.getText());
	      }
	    });
	    combo.addKeyListener(new KeyListener() {				
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub					
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				System.out.println(combo.getText());					
				if (arg0.keyCode == Byte_ascii.Ltr_a) {
					combo.setItem(0, "a");
					combo.setListVisible(true);
				}
				else if (arg0.keyCode == Byte_ascii.Ltr_b) {
					combo.setItem(0, "b");
					combo.setListVisible(true);
				}
				// System.out.println(combo.getText());
			}
		});
	
	    shell.open();
	    combo.setListVisible(true);
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch()) {
	        display.sleep();
	      }
	    }
	    display.dispose();
	}
	public static void Combo_composite() {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		GridLayout gridLayout = new GridLayout();
	    gridLayout.numColumns = 2;
	    gridLayout.makeColumnsEqualWidth = true;		
		shell.setLayout(gridLayout);
		final Text text = new Text(shell, SWT.BORDER);
		text.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		Text text2 = new Text(shell, SWT.BORDER);
		text2.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_END));
		shell.pack();
		shell.open();
		
		final Shell combo_shell = new Shell(display, SWT.ON_TOP);
		combo_shell.setLayout(new FillLayout());
		final Table combo_table = new Table(combo_shell, SWT.SINGLE);
		for (int i = 0; i < 5; i++) {
			new TableItem(combo_table, SWT.NONE);
		}

		text.addListener(SWT.KeyDown, new Listener() {			
			@Override public void handleEvent(Event event) {
				int index = -1;
				switch (event.keyCode) {
					case SWT.ARROW_DOWN:
						if (event.stateMask == SWT.ALT) {
							Rectangle text_bounds = display.map(shell, null, text.getBounds());
							combo_shell.setBounds(text_bounds.x, text_bounds.y + text_bounds.height, text_bounds.width, (text_bounds.height - 1) * combo_table.getItems().length);
							combo_shell.setVisible(true);
						} else {
							index = (combo_table.getSelectionIndex() + 1) % combo_table.getItemCount();
							combo_table.setSelection(index);
							event.doit = false;
						}
						break;
					case SWT.ARROW_UP:
						if (event.stateMask == SWT.ALT) {
							combo_shell.setVisible(false);
						} else {
							index = combo_table.getSelectionIndex() - 1;
							if (index < 0) index = combo_table.getItemCount() - 1;
							combo_table.setSelection(index);
							event.doit = false;
						}
						break;
					case SWT.CR:
						if (combo_shell.isVisible() && combo_table.getSelectionIndex() != -1) {
							text.setText(combo_table.getSelection()[0].getText());
							combo_shell.setVisible(false);
						}
						break;
					case SWT.ESC:
						combo_shell.setVisible(false);
						break;
				}			
			}
		});

		text.addListener(SWT.Modify, new Listener() {
			@Override public void handleEvent(Event event)  {
				String string = text.getText();
				if (string.length() == 0) {
					combo_shell.setVisible(false);
				} else {
					TableItem[] items = combo_table.getItems();
					for (int i = 0; i < items.length; i++) {
						items[i].setText(string + '-' + i);
					}
					
					Rectangle text_bounds = display.map(shell, null, text.getBounds());
					combo_shell.setBounds(text_bounds.x, text_bounds.y + text_bounds.height, text_bounds.width, (text_bounds.height - 1) * items.length);
					combo_shell.setVisible(true);
				}
			}
		});

		combo_table.addListener(SWT.DefaultSelection, new Listener() {
			@Override public void handleEvent(Event arg0) {
				text.setText(combo_table.getSelection()[0].getText());
				combo_shell.setVisible(false);			
			}
		});

		combo_table.addListener(SWT.KeyDown, new Listener() {
			@Override public void handleEvent(Event event) {
				if (event.keyCode == SWT.ESC) {
					combo_shell.setVisible(false);
				}
			}
		});

		final Swt_shell_hider shell_hider = new Swt_shell_hider(combo_shell);
		Listener focus_out_listener = new Listener() {
			@Override public void handleEvent(Event arg0) {
				if (display.isDisposed()) return;
				Control control = display.getFocusControl();
//				if (control == null || (control != text && control != combo_table)) {
//					combo_shell.setVisible(false);
//				}
				if (control == null || (control == text || control == combo_table)) {
					// combo_shell.setVisible(false);
					shell_hider.Active = true;
					display.asyncExec(shell_hider);
					//Thread t = new Thread(shell_hider); t.start();
					//Swt_shell_hider
				}
				
//				boolean combo_is_focus = combo_table.isFocusControl();
//				boolean text_is_focus = text.isFocusControl();
//				if (control == null || (control == text)) {
//					combo_shell.setVisible(false);
//				}
//				if (control == null || (control == combo_table)) {
//					combo_shell.setVisible(true);
//				}
			}
		};
		
		combo_table.addListener(SWT.FocusOut, focus_out_listener);
		text.addListener(SWT.FocusOut, focus_out_listener);

		Listener focus_in_listener = new Listener() {
			@Override public void handleEvent(Event arg0) {
				if (display.isDisposed()) return;
				Control control = display.getFocusControl();
				if (control == combo_table) {
					// combo_shell.setVisible(false);
					//display.asyncExec(shell_hider);
					shell_hider.Active = false;
					//Swt_shell_hider
				}
//				boolean combo_is_focus = combo_table.isFocusControl();
//				boolean text_is_focus = text.isFocusControl();
//				if (control == null || (control == text)) {
//					combo_shell.setVisible(false);
//				}
//				if (control == null || (control == combo_table)) {
//					combo_shell.setVisible(true);
//				}
			}
		};
		combo_table.addListener(SWT.FocusIn, focus_in_listener);
	
		shell.addListener(SWT.Move, new Listener() {
			@Override public void handleEvent(Event arg0) {
				combo_shell.setVisible(false);
			}
		}); 
	
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
}
class Swt_shell_hider implements Runnable {
	public boolean Active = true;

	private Shell combo_shell;
	public Swt_shell_hider(Shell combo_shell) {this.combo_shell = combo_shell;}
	@Override public void run() {
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if (Active) {
			combo_shell.setVisible(false);
		}
	}
}
