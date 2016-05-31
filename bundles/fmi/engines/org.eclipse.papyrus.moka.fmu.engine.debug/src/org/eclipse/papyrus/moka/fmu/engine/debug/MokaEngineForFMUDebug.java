package org.eclipse.papyrus.moka.fmu.engine.debug;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.papyrus.moka.discreteevent.DEScheduler;
import org.eclipse.papyrus.moka.fmu.engine.MokaEngineForFMUExport;
import org.eclipse.papyrus.moka.fmu.engine.control.FMUControlService;
import org.eclipse.papyrus.moka.fmu.engine.de.FMIPushPullStrategy;
import org.eclipse.papyrus.moka.fmu.engine.semantics.FMUObject;
import org.eclipse.papyrus.moka.fmu.engine.utils.FMUEngineUtils;
import org.eclipse.papyrus.moka.service.IMokaService;
import org.eclipse.papyrus.moka.service.MokaServiceRegistry;
import org.eclipse.papyrus.moka.timedfuml.actions._displayCurrentTimeAction;
import org.eclipse.papyrus.moka.utils.UMLPrimitiveTypesUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

public class MokaEngineForFMUDebug extends MokaEngineForFMUExport {

	@Override
	public void start(IProgressMonitor monitor) {
		if(!mode.equals(OperatingMode.QUIET)){
			// Initialize every service with the parameters of this particular run
			MokaServiceRegistry registry = MokaServiceRegistry.getInstance();
			registry.loadServices();
			for (IMokaService service : registry.getAllServices()) {
				service.init(this.launch, executionEntryPoint);
			}
		}
		Runnable execution = new Runnable() {
			public void run() {
				Class fmuClass = FMUEngineUtils.getFMUControlService().getFmuClass() ; 
				if (fmuClass != null) {
					startFMU(fmuClass);
					_displayCurrentTimeAction action = new _displayCurrentTimeAction() ;
					DEScheduler.init(-1.0, new FMIPushPullStrategy());
					DEScheduler.getInstance().pushPreStepAction(action);
					// Launches a new thread that acts as a pseudo master
					// This a simple "debugging" master controlled by end user
					Runnable master = new Runnable() {
						@Override
						public void run() {
							launchMaster();
							FMUControlService controlService = FMUEngineUtils.getFMUControlService() ;
							controlService.terminate();
						}
					};
					Thread masterThread = new Thread(master, "Moka - Master Thread") ;
					masterThread.start();

					FMUEngineUtils.getFMUControlService().getInstantiationLock().release();
					FMUEngineUtils.getFMUControlService().waitForTermination();
				}
			}
		};
		Thread mainThread = new Thread(execution, "Moka - Main thread");
		mainThread.start();
		try {
			mainThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	protected void launchMaster() {
		//Display display = Display.getDefault() ;
		Display display = new Display() ;
		Shell shell = new Shell(display);
		shell.setLayout(new RowLayout(SWT.VERTICAL));

		// Text and labels
		Composite textComposite = new Composite(shell, SWT.NONE) ;
		textComposite.setLayout(new FillLayout());

		final Label stepSizeLabel = new Label(textComposite, SWT.NONE) ;
		stepSizeLabel.setText("Step size:");

		final Text stepText = new Text(textComposite, SWT.NONE) ;
		stepText.setText("0.1");

		final Label currentTimeLabel = new Label(textComposite, SWT.NONE) ;
		currentTimeLabel.setText("Current time:");

		final Text currentTimeText = new Text(textComposite, SWT.NONE) ;
		currentTimeText.setText("");
		currentTimeText.setEditable(false);

		// Tables
		Composite tableComposite = new Composite(shell, SWT.NONE);
		final Label inputsLabel = new Label(tableComposite, SWT.NONE) ;
		inputsLabel.setText("inputs:");
		tableComposite.setLayout(new FillLayout());
		final Table inputTable = new Table(tableComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FILL);
		TableColumn inputColumnName = new TableColumn(inputTable, SWT.BORDER | SWT.FILL);
		inputColumnName.setWidth(100);
		inputColumnName.setResizable(true);
		inputColumnName.setText("Name");
		TableColumn inputColumnValue = new TableColumn(inputTable, SWT.BORDER | SWT.FILL);
		inputColumnValue.setWidth(100);
		inputColumnValue.setResizable(true);
		inputColumnValue.setText("Value");
		inputTable.setHeaderVisible(true);

		final TableEditor editor = new TableEditor(inputTable) ;
		editor.horizontalAlignment = SWT.LEFT ;
		editor.grabHorizontal = true ;
		editor.minimumWidth = 100 ;
		final int EDITABLECOLUMN = 1 ;

		// Buttons
		Composite buttonComposite = new Composite(shell, SWT.NONE) ;
		buttonComposite.setLayout(new FillLayout());

		final Button initButton = new Button(buttonComposite, SWT.NONE);
		initButton.setText("Init") ;

		final Button stepButton = new Button(buttonComposite, SWT.NONE);
		stepButton.setText("Step");
		stepButton.setEnabled(false);

		inputTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Clean up any previous editor control
				Control oldEditor = editor.getEditor();
				if (oldEditor != null) oldEditor.dispose();

				// Identify the selected row
				TableItem item = (TableItem)e.item;
				if (item == null) return;

				// The control that will be the editor must be a child of the Table
				Text newEditor = new Text(inputTable, SWT.NONE);
				newEditor.setText(item.getText(EDITABLECOLUMN));
				newEditor.addModifyListener(me -> {
					Text text = (Text)editor.getEditor();
					editor.getItem().setText(EDITABLECOLUMN, text.getText());
				});
				newEditor.addKeyListener(new KeyListener() {
					@Override
					public void keyReleased(KeyEvent arg0) {
						boolean validEntry = isValidEntry(editor.getItem().getText(0), editor.getItem().getText(1)) ;
						stepButton.setEnabled(validEntry);
						if (! validEntry) {
							editor.getItem().setForeground(display.getSystemColor(SWT.COLOR_RED));
						}
						else {
							editor.getItem().setForeground(display.getSystemColor(SWT.COLOR_BLACK));
						}
					}

					@Override
					public void keyPressed(KeyEvent arg0) {

					}
				});
				newEditor.selectAll();
				newEditor.setFocus();
				editor.setEditor(newEditor, item, EDITABLECOLUMN);
			}
		});



		final Label outputsLabel = new Label(tableComposite, SWT.NONE) ;
		outputsLabel.setText("outputs:");
		final Table outputTable = new Table(tableComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FILL);
		TableColumn outputColumnName = new TableColumn(outputTable, SWT.BORDER | SWT.FILL);
		outputColumnName.setWidth(100);
		outputColumnName.setResizable(true);
		outputColumnName.setText("Name");
		TableColumn outputColumnValue = new TableColumn(outputTable, SWT.BORDER | SWT.FILL);
		outputColumnValue.setWidth(100);
		outputColumnValue.setResizable(true);
		outputColumnValue.setText("Value");
		outputTable.setHeaderVisible(true);
		createInputOutputTableItems(inputTable, true) ;
		createInputOutputTableItems(outputTable, false) ;



		initButton.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				FMUControlService fmuControlService = FMUEngineUtils.getFMUControlService() ;
				fmuControlService.init();
				initButton.setEnabled(false);
				if (canParseDouble(stepText.getText())) {
					stepButton.setEnabled(true);
				}
				updateInputOutputTableItems(inputTable, true) ;
				updateInputOutputTableItems(outputTable, false) ;
				currentTimeText.setText("" + DEScheduler.getInstance().getCurrentTime());
				currentTimeText.redraw();
			}
		});

		stepButton.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				FMUControlService fmuControlService = FMUEngineUtils.getFMUControlService() ;
				double stepSize = 0.0 ;
				stepSize = new Double(stepText.getText()).doubleValue() ;
				setInputValues(inputTable) ;
				fmuControlService.doStep(DEScheduler.getInstance().getCurrentTime(), stepSize);
				updateInputOutputTableItems(outputTable, false) ;
				currentTimeText.setText("" + DEScheduler.getInstance().getCurrentTime());
				currentTimeText.redraw();
			}
		});

		stepText.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (canParseDouble(stepText.getText())) {
					if (! initButton.getEnabled()) {
						stepButton.setEnabled(true);
					}
				}
				else {
					stepButton.setEnabled(false);
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {

			}
		});

		//stepButton.setSize(100,25);

		//composite.pack();
		buttonComposite.pack();

		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	protected void createInputOutputTableItems(Table table, boolean isInput) {
		FMUObject _object = FMUEngineUtils.getFMUControlService().getFmuObject() ;
		for (Integer k : _object.fmiGetBools().keySet()) {
			Property p = _object.getIndexToUMLPropertyMap().get(k) ;
			if ((!isInput && FMUEngineUtils.isOutputPort(p)) || (isInput && FMUEngineUtils.isInputPort(p))) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] {p.getName(), ""});
			}
		}
		for (Integer k : _object.fmiGetIntegers().keySet()) {
			Property p = _object.getIndexToUMLPropertyMap().get(k) ;
			if ((!isInput && FMUEngineUtils.isOutputPort(p)) || (isInput && FMUEngineUtils.isInputPort(p))) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] {p.getName(), ""});
			}
		}
		for (Integer k : _object.fmiGetReals().keySet()) {
			Property p = _object.getIndexToUMLPropertyMap().get(k) ;
			if ((!isInput && FMUEngineUtils.isOutputPort(p)) || (isInput && FMUEngineUtils.isInputPort(p))) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] {p.getName(), ""});
			}
		}
		for (Integer k : _object.fmiGetStrings().keySet()) {
			Property p = _object.getIndexToUMLPropertyMap().get(k) ;
			if ((!isInput && FMUEngineUtils.isOutputPort(p)) || (isInput && FMUEngineUtils.isInputPort(p))) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] {p.getName(), ""});
			}
		}
	}

	protected void updateInputOutputTableItems(Table table, boolean isInput) {
		FMUObject _object = FMUEngineUtils.getFMUControlService().getFmuObject() ;
		int i = 0 ;
		for (Integer k : _object.fmiGetBools().keySet()) {
			Property p = _object.getIndexToUMLPropertyMap().get(k) ;
			if ((!isInput && FMUEngineUtils.isOutputPort(p)) || (isInput && FMUEngineUtils.isInputPort(p))) {
				TableItem item = table.getItem(i);
				item.setText(new String[] {p.getName(), ""+ _object.fmiGetBools().get(k)});
				i = i + 1 ;
			}
		}
		for (Integer k : _object.fmiGetIntegers().keySet()) {
			Property p = _object.getIndexToUMLPropertyMap().get(k) ;
			if ((!isInput && FMUEngineUtils.isOutputPort(p)) || (isInput && FMUEngineUtils.isInputPort(p))) {
				TableItem item = table.getItem(i);
				item.setText(new String[] {p.getName(), ""+ _object.fmiGetIntegers().get(k)});
				i = i + 1 ;
			}
		}
		for (Integer k : _object.fmiGetReals().keySet()) {
			Property p = _object.getIndexToUMLPropertyMap().get(k) ;
			if ((!isInput && FMUEngineUtils.isOutputPort(p)) || (isInput && FMUEngineUtils.isInputPort(p))) {
				TableItem item = table.getItem(i);
				item.setText(new String[] {p.getName(), ""+ _object.fmiGetReals().get(k)});
				i = i + 1 ;
			}
		}
		for (Integer k : _object.fmiGetStrings().keySet()) {
			Property p = _object.getIndexToUMLPropertyMap().get(k) ;
			if ((!isInput && FMUEngineUtils.isOutputPort(p)) || (isInput && FMUEngineUtils.isInputPort(p))) {
				TableItem item = table.getItem(i);
				item.setText(new String[] {p.getName(), ""+ _object.fmiGetStrings().get(k)});
				i = i + 1 ;
			}
		}
		table.redraw();
	}

	protected void setInputValues(Table inputTable) {
		Map<Integer, Boolean> boolMap = new HashMap<>() ;
		Map<Integer, String> stringMap = new HashMap<>() ;
		Map<Integer, Integer> integerMap = new HashMap<>() ;
		Map<Integer, Double> realMap = new HashMap<>() ;
		FMUObject fmuObject = FMUEngineUtils.getFMUControlService().getFmuObject() ;
		for (TableItem t : inputTable.getItems()) {
			String inputName = t.getText(0) ;
			String value = t.getText(1) ;
			Integer propertyIndex = fmuObject.getPropertyNameToIndexMap().get(inputName) ;
			Property property = fmuObject.getIndexToUMLPropertyMap().get(propertyIndex) ;
			if (UMLPrimitiveTypesUtils.getBoolean(property).equals(property.getType())) {
				Boolean booleanValue = Boolean.parseBoolean(value) ;
				boolMap.put(propertyIndex, booleanValue) ;
			}
			else if (UMLPrimitiveTypesUtils.getInteger(property).equals(property.getType())) {
				Integer integerValue = Integer.parseInt(value) ;
				integerMap.put(propertyIndex, integerValue) ;
			}
			else if (UMLPrimitiveTypesUtils.getString(property).equals(property.getType())) {
				stringMap.put(propertyIndex, value) ;
			}
			else if (UMLPrimitiveTypesUtils.getReal(property).equals(property.getType())) {
				Double realValue = Double.parseDouble(value) ;
				realMap.put(propertyIndex, realValue) ;
			}
		}
		fmuObject.fmiSetBools(boolMap);
		fmuObject.fmiSetIntegers(integerMap);
		fmuObject.fmiSetReals(realMap);
		fmuObject.fmiSetStrings(stringMap);
	}

	protected boolean canParseDouble(String s) {
		try {
			new Double(s) ;
		}
		catch (NumberFormatException e){
			return false ;
		}
		return true ;
	}

	protected boolean canParseInteger(String s) {
		try {
			new Integer(s) ;
		}
		catch (NumberFormatException e){
			return false ;
		}
		return true ;
	}

	protected boolean canParseBoolean(String s) {
		if (s != null) {
			return "true".equals(s.toLowerCase()) || "false".equals(s.toLowerCase()) ;
		}
		return false ;
	}

	private boolean isValidEntry(String propertyName, String value) {
		FMUObject fmuObject = FMUEngineUtils.getFMUControlService().getFmuObject() ;

		Integer index = fmuObject.getPropertyNameToIndexMap().get(propertyName) ;
		if (index != null) {
			Property property = fmuObject.getIndexToUMLPropertyMap().get(index) ;
			if (property != null) {
				if (UMLPrimitiveTypesUtils.getBoolean(property).equals(property.getType())) {
					return this.canParseBoolean(value) ;
				}
				else if (UMLPrimitiveTypesUtils.getInteger(property).equals(property.getType())) {
					return this.canParseInteger(value) ;
				}
				if (UMLPrimitiveTypesUtils.getReal(property).equals(property.getType())) {
					return this.canParseDouble(value) ;
				}
				if (UMLPrimitiveTypesUtils.getString(property).equals(property.getType())) {
					return true ;
				}
			}
		}
		return false;
	}
}
