package org.eclipse.papyrus.moka.datavisualization.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.NotFoundException;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForIEvaluationContext;
import org.eclipse.papyrus.moka.xygraph.common.model.XYGraphDescriptorModel;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphDescriptor;
import org.eclipse.papyrus.moka.datavisualization.profile.DataSource;
import org.eclipse.papyrus.moka.datavisualization.profile.DataValueSet;
import org.eclipse.papyrus.moka.datavisualization.profile.DoubleSeries;
import org.eclipse.papyrus.moka.datavisualization.profile.ValueSeries;
import org.eclipse.papyrus.moka.datavisualization.service.DataVisualizationService;
import org.eclipse.papyrus.moka.datavisualization.ui.GraphBuilderHelper;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListSelectionDialog;

/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  David LOPEZ BETANCUR (CEA LIST)
 *  Sebastien REVOL (CEA LIST)
 *
 *****************************************************************************/
public class CreateXYGraphFromDataSourceCommand extends RecordingCommand {

	private IEvaluationContext evaluationContext;
	protected DataSource dataSource;
	
	public CreateXYGraphFromDataSourceCommand(TransactionalEditingDomain domain, DataSource dataSource,
			IEvaluationContext context) {
		super(domain);
		this.evaluationContext = context;
		this.dataSource = dataSource;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	protected void doExecute() {
		
		DataValueSet dvs = dataSource.getValueSets().get(0);
		
		ListSelectionDialog ldg = new ListSelectionDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
				dvs, new DataSeriesContentProvider(),
				new LabelSeriesContentProvider(), "Select the series");

		if (ldg.open() == Window.OK) {
			Object[] selected = ldg.getResult();

			List<ValueSeries> series = new ArrayList<ValueSeries>(selected.length);
			for (int i = 0; i < selected.length; i++)
				series.add((ValueSeries) selected[i]);

			// Register variables and values
			// registerVariablesAndValues(series);
			// registerVariablesAndValues((DataValueSet)
			// res.getContents().get(0));

			// Create an XYGraph for it !
			try {
				// XYGraphDescriptor desc =
				// createDataValueSetGraph(ServiceUtilsForIEvaluationContext.getInstance().getModelSet(evaluationContext),
				// (DataValueSet) res.getContents().get(0));
				XYGraphDescriptor desc = createDataValueSetGraph(
						ServiceUtilsForIEvaluationContext.getInstance().getModelSet(evaluationContext), dvs, series);
				desc.setDataSource(dataSource);
				final IPageManager pageMngr = ServiceUtilsForIEvaluationContext.getInstance()
						.getIPageManager(evaluationContext);
				pageMngr.openPage(desc);
			} catch (ServiceException | NotFoundException e) {
				e.printStackTrace();
			}
		}
		return;
		/*
		
		ListSelectionDialog ldg = new ListSelectionDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), 
														(DataValueSet) res.getContents().get(0), 
														new DataSeriesContentProvider(), 
														new LabelSeriesContentProvider(), "Select the series");
		
		if( ldg.open() == Window.OK ){
			Object[] selected = ldg.getResult();
			
			ValueSeries[] series = new ValueSeries[selected.length];
			for( int i = 0; i < selected.length; i++ )
				series[i] = (ValueSeries)selected[i];
			
			//Register variables and values
			//registerVariablesAndValues(series);
			//registerVariablesAndValues((DataValueSet) res.getContents().get(0));
			
			//Create an XYGraph for it !
			try {
				//XYGraphDescriptor desc = createDataValueSetGraph(ServiceUtilsForIEvaluationContext.getInstance().getModelSet(evaluationContext), (DataValueSet) res.getContents().get(0));
				XYGraphDescriptor desc = createDataValueSetGraph(ServiceUtilsForIEvaluationContext.getInstance().getModelSet(evaluationContext), series);
				desc.setDataSource((DataValueSet) res.getContents().get(0));
				final IPageManager pageMngr = ServiceUtilsForIEvaluationContext.getInstance().getIPageManager(evaluationContext) ;
				pageMngr.openPage(desc);
			} catch (ServiceException | NotFoundException e) {
				e.printStackTrace();
			}
		}
		*/
	}

	class DataSeriesContentProvider implements IStructuredContentProvider{

		@Override
		public Object[] getElements(Object inputElement) {
			if( !(inputElement instanceof DataValueSet) )
				return null;
			
			DataValueSet set = (DataValueSet) inputElement;
			
			Object[] objects = new Object[set.getSeries().size()];
			int i = 0;
			for(ValueSeries serie : set.getSeries()){
				objects[i] = serie;
				i++;
			}
			
			return objects;
		}
		
	}
	
	class LabelSeriesContentProvider extends LabelProvider{
		@Override
		public String getText(Object element) {
			if( !(element instanceof ValueSeries) )
				return super.getText(element);
			
			ValueSeries serie = (ValueSeries) element;
			return serie.getVariableName();
		}
	}
	
	protected XYGraphDescriptor createDataValueSetGraph(ModelSet modelSet, DataValueSet dvs, List<ValueSeries> visibles) throws NotFoundException {
		final XYGraphDescriptor xyGraph = GraphBuilderHelper.buildFromSeries(dvs.getSeries(), visibles);

		// Save the model in the associated resource
		final XYGraphDescriptorModel model = (XYGraphDescriptorModel) modelSet.getModelChecked(XYGraphDescriptorModel.MODEL_ID);
		xyGraph.setContext(dataSource.getBase_DataType().eContainer());
		model.addXYGraphDescriptor(xyGraph);
		
		return xyGraph;
	}
	
	protected XYGraphDescriptor buildGraph(DataValueSet dvs) {
		return GraphBuilderHelper.buildFromDataValueSet(dvs);
	}
	
	protected void registerVariablesAndValues(ValueSeries[] series) {
		//Get time series;
		EList<Double> times = null;
		for(ValueSeries serie : series){
			if( "time".equalsIgnoreCase(serie.getVariableName()) ){
				DoubleSeries doubleSerie = (DoubleSeries)serie;
				times = doubleSerie.getValues();
				break;
			}
		}
		
		if( times == null )
			throw new RuntimeException("There's not a 'time' variable");
		
		DataVisualizationService service = DataVisualizationService.getInstance();
		
		for(ValueSeries serie : series){
			if( "time".equalsIgnoreCase(serie.getVariableName()) ) 
				continue;
			
			service.registerVariable(serie.getVariableName());
			
			DoubleSeries doubleSerie = (DoubleSeries)serie;
			service.addBatch(serie.getVariableName(), times, doubleSerie.getValues());
		}
	}

	
	protected void registerVariablesAndValues(DataValueSet set) {
		//Get time series;
		EList<Double> times = null;
		for(ValueSeries serie : set.getSeries()){
			if( "time".equalsIgnoreCase(serie.getVariableName()) ){
				DoubleSeries doubleSerie = (DoubleSeries)serie;
				times = doubleSerie.getValues();
				break;
			}
		}
		
		if( times == null )
			throw new RuntimeException("There's not a 'time' variable");
		
		DataVisualizationService service = DataVisualizationService.getInstance();
		
		for(ValueSeries serie : set.getSeries()){
			if( "time".equalsIgnoreCase(serie.getVariableName()) ) 
				continue;
			
			service.registerVariable(serie.getVariableName());
			
			DoubleSeries doubleSerie = (DoubleSeries)serie;
			
			service.addBatch(serie.getVariableName(), times, doubleSerie.getValues());
		}
	}
}