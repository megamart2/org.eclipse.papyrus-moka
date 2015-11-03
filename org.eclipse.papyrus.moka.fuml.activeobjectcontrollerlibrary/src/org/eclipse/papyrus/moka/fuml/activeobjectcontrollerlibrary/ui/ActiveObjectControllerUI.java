/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.moka.fuml.activeobjectcontrollerlibrary.ui;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.InvocationActions.CS_SignalInstance;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_InteractionPoint;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_Object;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.BooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.FeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.RealValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.StringValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.UnlimitedNaturalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Feature;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Reception;
import org.eclipse.uml2.uml.Signal;

/**
 * This class create an UI for the Active Object Controller Library in the Debug view of Papyrus.
 * When an execution is start and a register signal is send, this UI is enable.
 *
 * This UI allow to send signals for registered objects during an execution.
 * User can choice which signal he want to send, on which port and for which object.
 * Also, signals with properties can be edited with an integrated table.
 */
public class ActiveObjectControllerUI extends ViewPart {

	/**
	 * This composite
	 */
	protected Composite self;

	/**
	 * SWT List which contains registered objects
	 */
	protected List objectsList;

	/**
	 * SWT List which contains UML ports for selected object
	 */
	protected List portsList;

	/**
	 * SWT List which contains UML signals for selected object/port
	 */
	protected List signalsList;

	/**
	 * SWT Button which allow to send signal after selection
	 */
	protected Button btnSend;

	/**
	 * SWT Button (checkBox) which allow to hide signal without explicit reception
	 */
	protected Button unexplicitSignal;

	/**
	 * SWT Table which contains properties for selected signals
	 */
	protected Table table;

	/**
	 * SWT TableEditor, allow to edit TableItem containing in the SWT Table
	 */
	protected TableEditor editor;

	/**
	 * SWT Image which represent status (wrong) for TableItem
	 *
	 */
	protected Image redStatus;

	/**
	 * SWT Image which represent status (correct) for TableItem
	 */
	protected Image greenStatus;

	/**
	 * Instance of the class
	 */
	protected static ActiveObjectControllerUI eInstance;

	/**
	 * UML Model of the execution context
	 */
	protected Model model;

	/**
	 * UML CS_InteractionPoint which represent a no selection of port for selected object
	 */
	protected final CS_InteractionPoint noPort = null;

	/**
	 * Name displaying in SWT portsList for see @noPort
	 */
	protected final String noPortName = "<none>";

	/**
	 * Status of @unexplicitSignal state
	 */
	protected boolean isChecked;

	/**
	 * Name of registeredObjects containing in SWT @objectsList
	 */
	protected String registeredObjectName;

	/**
	 * Represent the default display that allow to edit SWT components in runtime
	 */
	protected Display display;

	/**
	 * UML Signal that represent selected signal by the user
	 */
	protected Signal selectedSignal;

	/**
	 * Java List containing fUML Object_ synchronized with @objectsList
	 */
	protected static java.util.List<Object_> registeredObjects = new ArrayList<Object_>();

	/**
	 * Java List containing PSCS CS_InteractionPoint synchronized with @portsList
	 */
	protected static java.util.List<CS_InteractionPoint> ports = new ArrayList<CS_InteractionPoint>();

	/**
	 * Java List containing UML Signal synchronized with @signalsList
	 */
	protected static java.util.List<Signal> signals = new ArrayList<Signal>();

	/**
	 * Integer TableEditor status
	 */
	protected boolean integerStatus = true;

	/**
	 * Real TableEditor status
	 */
	protected boolean realStatus = true;

	/**
	 * Boolean TableEditor status
	 */
	protected boolean booleanStatus = true;

	/**
	 * UnlimitedNatural TableEditor status
	 */
	protected boolean unlimitedNaturalStatus = true;

	/**
	 * Class ID
	 */
	public static final String ID = "org.eclipse.papyrus.moka.fuml.activeobjectcontrollerlibrary.ui.ActiveObjectControllerUI";

	/**
	 * Constructor Initialize eInstance
	 *
	 */
	public ActiveObjectControllerUI() {
		eInstance = this;
	}

	/**
	 * Get active object from model. Then, add them on the registeredObject list.
	 *
	 * @param object
	 * @throws FileNotFoundException
	 */
	public void register(Object object) {
		Object arg = object;
		if (arg instanceof CS_Reference) {
			CS_Reference ref = (CS_Reference) arg;
			if (ref.compositeReferent instanceof CS_Object) {
				CS_Object referent = ref.compositeReferent;
				// Add the registered object to the Object_ list
				registeredObjects.add(referent);

				// get the execution context
				if (this.model == null) {
					this.model = referent.getTypes().get(0).getModel();
				}

				this.display = Display.getDefault();

				InputStream redStream = ActiveObjectControllerUI.class.getResourceAsStream("/icons/error.gif");
				this.redStatus = new Image(this.display, redStream);

				InputStream greenStream = ActiveObjectControllerUI.class.getResourceAsStream("/icons/validate.gif");
				this.greenStatus = new Image(this.display, greenStream);

				// Get registered object Name
				this.registeredObjectName = referent.getTypes().get(0).getName();

				this.display.syncExec(new Runnable() {

					@Override
					public void run() {
						// Add registeredObject in the SWT List
						objectsList.add(registeredObjectName);
						unexplicitSignal.setEnabled(true);
						self.layout();

						// Create graphic red image
						GC gc_redStatus = new GC(redStatus);
						gc_redStatus.drawImage(redStatus, 10, 10);
						gc_redStatus.dispose();

						// Create graphic green image
						GC gc_greenStatus = new GC(greenStatus);
						gc_greenStatus.drawImage(greenStatus, 10, 10);
						gc_greenStatus.dispose();
					}
				});
			}
		}
	}

	/**
	 * When the ActiveObjectController receives a terminateSignal from the runtime,
	 * Clear contents of the view and disable buttons and dispose the view.
	 */
	public static void terminate() {
		eInstance.objectsList.removeAll();
		eInstance.objectsList.removeAll();
		registeredObjects.clear();
		eInstance.portsList.removeAll();
		ports.clear();
		eInstance.signalsList.removeAll();
		signals.clear();
		eInstance.table.removeAll();
		eInstance.disabledTableHeaderColumn();
		eInstance.disabledSendButton();
		eInstance.disabledUnexplicitSignalButton();
		eInstance.dispose();
	}

	/**
	 * Create Part Control on Debug Perspective
	 *
	 */
	@Override
	public void createPartControl(Composite parent) {

		/* Create the main composite which contains all elements */
		Composite main = new Composite(parent, SWT.NONE);
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.VERTICAL;
		main.setLayout(fillLayout);

		GridData gdComponents = new GridData(SWT.FILL, SWT.FILL, true, true);

		/* New configuration of the main composite */
		this.self = new Composite(main, SWT.NONE);
		this.self.setLayout(new GridLayout());
		this.self.setLayoutData(gdComponents);

		/* Composite which contains all SWT List and SWT Table */
		Composite cp_Lists = new Composite(this.self, SWT.FILL);
		GridData gd_cpLists = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_cpLists.minimumHeight = 70;
		cp_Lists.setLayoutData(gd_cpLists);
		cp_Lists.setLayout(new GridLayout(4, true));

		/*
		 * Composite which contains list of objects and a label "Objects:"
		 */
		Composite cp_Objects = new Composite(cp_Lists, SWT.NONE);
		cp_Objects.setLayout(new GridLayout());
		cp_Objects.setLayoutData(gdComponents);

		// Label before object List
		Label lblObjects = new Label(cp_Objects, SWT.NONE);
		lblObjects.setText("Objects:");

		// List which contains objects
		this.objectsList = new List(cp_Objects, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		this.objectsList.setToolTipText("The list of registered active objects. By selecting one object, the list of signals that this object can receive will be updated.");
		this.objectsList.setLayoutData(gdComponents);

		/*
		 * Listener on listObjects If selected object have port, the port is
		 * added on portsList. Else, add right signal on signalsList.
		 */
		this.objectsList.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				signalsList.removeAll();
				signals.clear();
				portsList.removeAll();
				ports.clear();
				portsList.add(noPortName);
				ports.add(noPort);
				table.removeAll();

				if (editor.getEditor() != null) {
					editor.getEditor().dispose();
				}
				disabledTableHeaderColumn();

				boolean hasPort = false;
				if (objectsList.getSelectionCount() > 0) {
					int selectionIndex = objectsList.getSelectionIndices()[0];
					Object_ target = registeredObjects.get(selectionIndex);
					for (Classifier c : target.getTypes()) {
						for (Property prop : c.getAttributes()) {
							if (prop instanceof Port) {
								hasPort = true;
								;
								Port p = (Port) prop;
								FeatureValue f = target.getFeatureValue(p);
								for (Value v : f.values) {
									if (v instanceof CS_InteractionPoint) {
										int index = f.values.indexOf(v) + 1;
										portsList.add(p.getName() + "[" + index + "]");
										ports.add((CS_InteractionPoint) v);
									}
								}
							}
						}
						if (!hasPort) {
							getAllSignals();
						}
					}

					display.syncExec(new Runnable() {
						@Override
						public void run() {
							portsList.select(0);
							btnSend.setEnabled(false);
						}
					});

				}
			}
		});

		/* Composite which contains list of ports and label */
		Composite cp_Ports = new Composite(cp_Lists, SWT.NONE);
		cp_Ports.setLayout(new GridLayout());
		cp_Ports.setLayoutData(gdComponents);

		// Label before the list
		Label lblPorts = new Label(cp_Ports, SWT.NONE);
		lblPorts.setText("Ports:");

		/* List which contains adequate ports. */
		this.portsList = new List(cp_Ports, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		this.portsList.setToolTipText("The list of port that the selected object, if any, can receive.");
		this.portsList.setLayoutData(gdComponents);

		/*
		 * Listener on listPorts If a port is selected, add all signals
		 * available. Else, add all signals available for the parent (object)
		 */
		this.portsList.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				signalsList.removeAll();
				signals.clear();
				table.removeAll();
				if (editor.getEditor() != null) {
					editor.getEditor().dispose();
				}
				disabledTableHeaderColumn();
				getAllSignals();
				disabledSendButton();
			}

		});

		/*
		 * Composite which contains label and listSignals
		 */
		Composite cp_Signals = new Composite(cp_Lists, SWT.NONE);
		cp_Signals.setLayout(new GridLayout());
		cp_Signals.setLayoutData(gdComponents);

		// Label before the list
		Label lblSignals = new Label(cp_Signals, SWT.NONE);
		lblSignals.setText("Signals:");

		/* List (SWT) of signals */
		this.signalsList = new List(cp_Signals, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		this.signalsList.setToolTipText("The list of signals that the selected object, if any, can receive.");
		this.signalsList.setLayoutData(gdComponents);

		/*
		 * Listener on listSignals If signal has property, create TableItem which represent them.
		 *
		 * Activate sending button.
		 */
		this.signalsList.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (signalsList.getSelectionCount() > 0) {
					int signalSelectionIndex = signalsList.getSelectionIndices()[0];
					selectedSignal = signals.get(signalSelectionIndex);
					java.util.List<Feature> features = selectedSignal.getFeatures();
					boolean alreadyExist = false;
					if (editor.getEditor() != null) {
						editor.getEditor().dispose();
					}
					if (features.size() > 0) {
						for (Feature feature : features) {
							if (feature instanceof Property) {
								Property p = (Property) feature;
								if (p.getType() instanceof PrimitiveType) {
									if (table.getItems().length > 0) {
										for (int i = 0; i < table.getItems().length; i++) {
											TableItem cddItem = table.getItems()[i];
											if (cddItem.getText(0).equals(p.getName())) {
												alreadyExist = true;
												i = table.getItems().length;
											}
										}
									}
									if (!alreadyExist) {
										TableItem item = new TableItem(table, SWT.NONE);
										// Add default value for each type
										if (p.getType().getName().equals("Integer")) {
											item.setText(new String[] { p.getName(), "0" });
											item.setImage(greenStatus);
										} else if (p.getType().getName().equals("String")) {
											item.setText(new String[] { p.getName(), "" });
											item.setImage(greenStatus);
										} else if (p.getType().getName().equals("Boolean")) {
											item.setText(new String[] { p.getName(), "true" });
											item.setImage(greenStatus);
										} else if (p.getType().getName().equals("Real")) {
											item.setText(new String[] { p.getName(), "0.0" });
											item.setImage(greenStatus);
										} else if (p.getType().getName().equals("UnlimitedNatural")) {
											item.setText(new String[] { p.getName(), "0" });
											item.setImage(greenStatus);
										}

										display.syncExec(new Runnable() {

											@Override
											public void run() {
												table.setHeaderVisible(true);
											}
										});
										enabledSendButton();
									}
								}
							}
						}
					} else {
						if (table.getItemCount() > 0) {
							table.removeAll();
						}

						display.syncExec(new Runnable() {
							@Override
							public void run() {
								table.setHeaderVisible(false);
							}
						});
						enabledSendButton();
					}
				}
			}
		});

		/* Composite which contains table to edit signal's arguments */
		Composite cp_Table = new Composite(cp_Lists, SWT.FILL);
		cp_Table.setLayout(new GridLayout());
		cp_Table.setLayoutData(gdComponents);

		// Label before the list
		Label lblTable = new Label(cp_Table, SWT.NONE);
		lblTable.setText("Arguments:");

		/* Table to edit signal's arguments */
		this.table = new Table(cp_Table, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FILL);
		this.table.setLayoutData(gdComponents);
		TableColumn columnName = new TableColumn(this.table, SWT.BORDER | SWT.FILL);
		columnName.setWidth(100);
		columnName.setResizable(true);
		columnName.setText("Name");
		TableColumn columnValue = new TableColumn(this.table, SWT.BORDER | SWT.FILL);
		columnValue.setWidth(100);
		columnValue.setResizable(true);
		columnValue.setText("Value");
		this.table.setHeaderVisible(false);

		// Create the editor for the table
		createTableEditor();

		// Sending Button
		this.btnSend = new Button(this.self, SWT.CENTER);
		this.btnSend.setToolTipText("Send the selected signal to the selected object.");
		GridData gd_btnSend = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd_btnSend.minimumHeight = 30;
		this.btnSend.setLayoutData(gd_btnSend);
		this.btnSend.setText("Send");
		this.btnSend.setEnabled(false);


		/*
		 * Listener on Sending Button.
		 * Get elements selected on lists, then create a signal Instance to send.
		 * If selected signal has property, evaluate values from the table and add them to the signal instance.
		 * Then, send it.
		 */
		btnSend.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				if (signalsList.getSelection().length > 0 && portsList.getSelection().length > 0 && objectsList.getSelection().length > 0) {
					int objectSelectionIndex = objectsList.getSelectionIndices()[0];
					Object_ target = registeredObjects.get(objectSelectionIndex);
					int portSelectionIndex = portsList.getSelectionIndices()[0];
					CS_InteractionPoint interactionPoint = null;
					if (portSelectionIndex >= 0) {
						interactionPoint = ports.get(portSelectionIndex);
					}
					int signalSelectionIndex = signalsList.getSelectionIndices()[0];
					Signal signal = signals.get(signalSelectionIndex);

					CS_SignalInstance signalInstance = new CS_SignalInstance();
					signalInstance.type = signal;
					signalInstance.createFeatureValues();

					evaluate(table.getItems(), signalInstance);

					if (interactionPoint != null) {
						interactionPoint.send(signalInstance);
					}
					else {
						target.send(signalInstance);
					}
				}
			}
		});

		// Checkbox option
		unexplicitSignal = new Button(this.self, SWT.CHECK);
		unexplicitSignal.setText("Hide signals without explicit receptions");
		unexplicitSignal.setSelection(true);

		isChecked = true;
		unexplicitSignal.setEnabled(false);
		unexplicitSignal.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		/*
		 * If is checked, show all signals that could be sending by the active
		 * object.
		 */
		unexplicitSignal.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				signalsList.removeAll();
				signals.clear();
				getAllSignals();
			}
		});

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.self, "org.eclipse.papyrus.moka.fuml.ui.activeobjectcontrollerlibrary.viewer");
	}

	/**
	 * Evaluate value for item selected in the table
	 * Editors supported : Integer, String, Boolean, Real
	 * Editors shall be supported : UnlimitedNatural, Enumeration
	 *
	 * @param items
	 *            : representing table items in the view
	 * @param signalInstance
	 *            : signalInstance corresponding of parent of table items.
	 */
	protected void evaluate(TableItem[] items, CS_SignalInstance signalInstance) {
		/**
		 * Edit featureValues of signalInstance for each TableItems in the table.
		 * Like the editor, browse through featureValues of signalInstance to retrieve which is corresponding to the TableItem.
		 * Then, edit the featureValue.
		 */
		for (int i = 0; i < items.length; i++) {
			TableItem it = items[i];
			String text = it.getText(1);
			FeatureValue cddFeatureValue = signalInstance.featureValues.get(i);
			if (cddFeatureValue.feature instanceof Property) {
				Property p = (Property) cddFeatureValue.feature;
				if (p.getType() instanceof PrimitiveType) {
					if (p.getType().getName().equals("Integer")) {
						Integer cddInteger = Integer.parseInt(text);
						IntegerValue cddValue = new IntegerValue();
						cddValue.value = cddInteger;
						cddFeatureValue.values.add(cddValue);
					} else if (p.getType().getName().equals("UnlimitedNatural")) {
						if (text.equals("*")) {
							UnlimitedNaturalValue cddValue = new UnlimitedNaturalValue();
							cddValue.value = -1;
							cddFeatureValue.values.add(cddValue);
						} else {
							Integer cddInteger = Integer.parseInt(text);
							IntegerValue cddValue = new IntegerValue();
							cddValue.value = cddInteger;
							cddFeatureValue.values.add(cddValue);
						}
					} else if (p.getType().getName().equals("String")) {
						StringValue cddValue = new StringValue();
						cddValue.value = text;
						cddFeatureValue.values.add(cddValue);
					} else if (p.getType().getName().equals("Boolean")) {
						Boolean cddBoolean = Boolean.parseBoolean(text);
						BooleanValue cddValue = new BooleanValue();
						cddValue.value = cddBoolean;
						cddFeatureValue.values.add(cddValue);
					} else if (p.getType().getName().equals("Real")) {
						Double cddDouble = Double.parseDouble(text);
						RealValue cddValue = new RealValue();
						cddValue.value = cddDouble;
						cddFeatureValue.values.add(cddValue);
					}
				}
			}
		}
	}

	/**
	 * Set and configure the table editors for the following supported types :
	 * - Integer, Boolean, String, Real
	 *
	 */
	protected void createTableEditor() {
		editor = new TableEditor(this.table);
		// The editor must have the same size as the cell and must
		// not be any smaller than 50 pixels.
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 50;
		// editing the second column
		final int EDITABLECOLUMN = 1;

		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Clean up any previous editor control
				Control oldEditor = editor.getEditor();
				if (oldEditor != null) {
					oldEditor.dispose();
				}

				// Identify the selected row
				TableItem item = (TableItem) e.item;
				if (item == null) {
					return;
				}

				// The control that will be the editor must be a child of the table
				Text newEditor = new Text(table, SWT.NONE);
				newEditor.setText(item.getText(EDITABLECOLUMN));

				newEditor.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent me) {
						/**
						 * First, get edited text by user from the TableEditor.
						 * Then, browse through featuresValue of selected signal which featureValue is edited.
						 * Finally, parse the edited text with the right editor and display correction status (validated or not).
						 */

						Text text = (Text) editor.getEditor();
						editor.getItem().setText(EDITABLECOLUMN, text.getText());
						enabledSendButton();
						String cddText = editor.getItem().getText(EDITABLECOLUMN);
						java.util.List<Feature> features = selectedSignal.getFeatures();
						for (Feature feature : features) {
							if (feature instanceof Property) {
								Property p = (Property) feature;
								if (p.getType() instanceof PrimitiveType) {
									if (editor.getItem().getText(0).equals(p.getName())) {
										if (p.getType().getName().equals("Integer")) {
											try {
												Integer.parseInt(cddText);
												setStatus(editor.getEditor(), editor.getItem(), greenStatus, true);
												integerStatus = true;
											}
											catch (Exception e) {
												setStatus(editor.getEditor(), editor.getItem(), redStatus, false);
												integerStatus = false;
											}
										} else if (p.getType().getName().equals("Boolean")) {
											if (cddText.equalsIgnoreCase("true") || cddText.equalsIgnoreCase("false")) {
												setStatus(editor.getEditor(), editor.getItem(), greenStatus, true);
												booleanStatus = true;
											} else {
												setStatus(editor.getEditor(), editor.getItem(), redStatus, false);
												booleanStatus = false;
											}
										} else if (p.getType().getName().equals("Real")) {
											try {
												Double.parseDouble(cddText);
												setStatus(editor.getEditor(), editor.getItem(), greenStatus, true);
												realStatus = true;
											}
											catch (Exception e) {
												setStatus(editor.getEditor(), editor.getItem(), redStatus, false);
												realStatus = false;
											}
										} else if (p.getType().getName().equals("UnlimitedNatural")) {
											try {
												if (cddText.equals("*") || Integer.parseInt(cddText) > 0) {
													setStatus(editor.getEditor(), editor.getItem(), greenStatus, true);
													unlimitedNaturalStatus = true;
												} else {
													setStatus(editor.getEditor(), editor.getItem(), redStatus, false);
													unlimitedNaturalStatus = false;
												}
											}
											catch (Exception e) {
												setStatus(editor.getEditor(), editor.getItem(), redStatus, false);
												unlimitedNaturalStatus = false;
											}
										}
									}
								}
							}
						}
						if (integerStatus && realStatus && booleanStatus && unlimitedNaturalStatus) {
							enabledSendButton();
						} else {
							disabledSendButton();
						}
					}
				});
				newEditor.selectAll();
				newEditor.setFocus();
				editor.setEditor(newEditor, item, EDITABLECOLUMN);
			}
		});

	}

	/**
	 * Set the status of the table :
	 * - Change font color,
	 * - Add status image.
	 *
	 */
	protected void setStatus(Control editor, TableItem item, Image image, boolean status) {
		if (status) {
			editor.setForeground(new Color(display, 0, 0, 0));
			item.setForeground(new Color(display, 0, 0, 0));
			item.setImage(image);
		} else {
			editor.setForeground(new Color(display, 255, 0, 0));
			item.setForeground(new Color(display, 255, 0, 0));
			item.setImage(image);
		}
	}

	/**
	 * Return the instance of ActiveObjectControllerUI
	 *
	 * @return instance of ActiveObjectControllerUI
	 */
	public static ActiveObjectControllerUI getInstance() {
		return eInstance;
	}

	/**
	 * This method enable the send button of the view
	 *
	 */
	protected void enabledSendButton() {
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				btnSend.setEnabled(true);
			}
		});
	}

	/**
	 * This method disable the send button of the view
	 */
	protected void disabledSendButton() {
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				btnSend.setEnabled(false);
			}
		});
	}

	/**
	 * This method enable the checkBox of the view
	 */
	protected void enabledUnexplicitSignalButton() {
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				unexplicitSignal.setEnabled(true);
			}
		});
	}

	/**
	 * This method disable the checkBox of the view
	 */
	protected void disabledUnexplicitSignalButton() {
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				unexplicitSignal.setEnabled(false);
			}
		});
	}

	/**
	 * This method display headers of the table
	 */
	protected void enabledTableHeaderColumn() {
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				table.setHeaderVisible(true);
			}
		});
	}

	/**
	 * This method disable displaying headers of the table
	 */
	protected void disabledTableHeaderColumn() {
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				table.setHeaderVisible(false);
			}
		});
	}

	/**
	 * This method retrieves explicit or implicit signals for selection.
	 * First, verifies if the checkBox is checked :
	 * - if it's checked, display signals with explicit reception
	 * - if it's not, display all signals in the model
	 *
	 */
	protected void getAllSignals() {
		/**
		 * First, check which kind of signals is needed to be displayed :
		 * If it's signals with explicit reception, there is two way to select them :
		 * - If a port is selected, get the reception for the provided interface of the port.
		 * - Else, search through the registered object selected an explicit reception (on port or not).
		 *
		 * If don't hide signals without explicit reception, we search through the model all defined signals.
		 *
		 */
		if (unexplicitSignal.getSelection()) {
			isChecked = true;
			if (objectsList.getSelection().length > 0) {
				int objectSelectionIndex = objectsList.getSelectionIndices()[0];
				Object_ target = registeredObjects.get(objectSelectionIndex);
				if (portsList.getSelection().length > 0) {
					int portSelectionIndex = portsList.getSelectionIndices()[0];
					CS_InteractionPoint interactionPoint = ports.get(portSelectionIndex);
					if (interactionPoint == null) {
						for (Classifier c : target.getTypes()) {
							for (NamedElement n : c.getMembers()) {
								if (n instanceof Port) {
									Port cddPort = (Port) n;
									for (Interface i : cddPort.getProvideds()) {
										for (NamedElement cddNamedElement : i.getMembers()) {
											if (cddNamedElement instanceof Reception) {
												Reception r = (Reception) cddNamedElement;
												String signalName = "<<Signal>>" + r.getSignal().getName();
												signalsList.add(signalName);
												signals.add(r.getSignal());
											}
										}
									}
								} else if (n instanceof Reception) {
									Reception r = (Reception) n;
									String signalName = "<<Signal>>" + r.getSignal().getName();
									signalsList.add(signalName);
									signals.add(r.getSignal());
								}
							}
						}
					} else {
						for (Interface i : interactionPoint.definingPort.getProvideds()) {
							for (NamedElement n : i.getMembers()) {
								if (n instanceof Reception) {
									Reception r = (Reception) n;
									String signalName = "<<Signal>>" + r.getSignal().getName();
									signalsList.add(signalName);
									signals.add(r.getSignal());
								}
							}
						}
					}

				} else {
					for (Classifier c : target.getTypes()) {
						for (NamedElement n : c.getMembers()) {
							if (n instanceof Reception) {
								Reception r = (Reception) n;
								String signalName = "<<Signal>>" + r.getSignal().getName();
								signalsList.add(signalName);
								signals.add(r.getSignal());
							}
						}
					}
				}
			}
		} else {
			isChecked = false;
			for (Iterator<EObject> i = model.eResource().getAllContents(); i.hasNext();) {
				EObject n = i.next();
				if (n instanceof Signal) {
					Signal s = (Signal) n;
					signalsList.add("<<Signal>>" + s.getName());
					signals.add(s);
				}
			}
		}
	}

	@Override
	public void setFocus() {
	}
}
