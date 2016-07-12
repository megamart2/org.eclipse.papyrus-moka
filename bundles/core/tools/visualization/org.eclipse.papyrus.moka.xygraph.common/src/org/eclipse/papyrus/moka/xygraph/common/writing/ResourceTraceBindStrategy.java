package org.eclipse.papyrus.moka.xygraph.common.writing;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.papyrus.moka.xygraph.mapping.common.XYGraphBinder;
import org.eclipse.papyrus.moka.xygraph.mapping.writing.TraceUpdateStrategy;
import org.eclipse.papyrus.moka.xygraph.model.reflection.TraceStructuralFeature;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.TraceDescriptor;


public class ResourceTraceBindStrategy extends ResourceBaseUpdateStrategy implements TraceUpdateStrategy {

	private TransactionalEditingDomain domain;
	private CompoundCommand cc;
	
	@Override
	public void updateTraceDescriptor(Trace trace, XYGraphBinder map) {
//		TraceDescriptor tDesc = map.getDescriptorFor(trace);
//		
//		domain = getTransactionalEditingDomain(map.getXYGraphDescriptor().eResource());
//		
//		CompoundCommand cc = new CompoundCommand();
//		
//		appendSetCommand(cc, domain, tDesc, XYGraphPackage.eINSTANCE.getTraceDescriptor_AreaAlpha(), trace.getAreaAlpha());
//		appendSetCommand(cc, domain, tDesc, XYGraphPackage.eINSTANCE.getTraceDescriptor_BaseLine(), XYGraphMappingHelper.descTraceBaseLine(trace.getBaseLine()));
//		appendSetCommand(cc, domain, tDesc, XYGraphPackage.eINSTANCE.getTraceDescriptor_ErrorBarCapWidth(), trace.getErrorBarCapWidth());
//		appendSetCommand(cc, domain, tDesc, XYGraphPackage.eINSTANCE.getTraceDescriptor_ErrorBarColor(), XYGraphMappingHelper.mapColor(trace.getErrorBarColor()));
//		appendSetCommand(cc, domain, tDesc, XYGraphPackage.eINSTANCE.getTraceDescriptor_LineWidth(), trace.getLineWidth());
//		appendSetCommand(cc, domain, tDesc, XYGraphPackage.eINSTANCE.getTraceDescriptor_PointSize(), trace.getPointSize());
//		appendSetCommand(cc, domain, tDesc, XYGraphPackage.eINSTANCE.getTraceDescriptor_PointStyle(), XYGraphMappingHelper.descPointStyle( trace.getPointStyle() ));
//		appendSetCommand(cc, domain, tDesc, XYGraphPackage.eINSTANCE.getTraceDescriptor_TraceColor(), XYGraphMappingHelper.mapColor(trace.getTraceColor()));
//		appendSetCommand(cc, domain, tDesc, XYGraphPackage.eINSTANCE.getTraceDescriptor_TraceType(), XYGraphMappingHelper.descTraceType(trace.getTraceType()));
//		appendSetCommand(cc, domain, tDesc, XYGraphPackage.eINSTANCE.getTraceDescriptor_XErrorBarType(), XYGraphMappingHelper.descErrorBarType(trace.getXErrorBarType()));
//		appendSetCommand(cc, domain, tDesc, XYGraphPackage.eINSTANCE.getTraceDescriptor_YErrorBarType(), XYGraphMappingHelper.descErrorBarType(trace.getYErrorBarType()));
//		appendSetCommand(cc, domain, tDesc, XYGraphPackage.eINSTANCE.getTraceDescriptor_AntiAliasing(), trace.isAntiAliasing() );
//		appendSetCommand(cc, domain, tDesc, XYGraphPackage.eINSTANCE.getTraceDescriptor_DrawYErrorInArea(), trace.isDrawYErrorInArea() );
//		appendSetCommand(cc, domain, tDesc, XYGraphPackage.eINSTANCE.getTraceDescriptor_ErrorBarEnabled(), trace.isErrorBarEnabled() );
//		appendSetCommand(cc, domain, tDesc, XYGraphPackage.eINSTANCE.getTraceDescriptor_Name(), trace.getName() );
//
//		appendSetCommand(cc, domain, tDesc, XYGraphPackage.eINSTANCE.getTraceDescriptor_XAxis(), map.getDescriptorFor(trace.getXAxis()) );
//		appendSetCommand(cc, domain, tDesc, XYGraphPackage.eINSTANCE.getTraceDescriptor_YAxis(), map.getDescriptorFor(trace.getYAxis()) );
//		
//		if( cc.canExecute() )
//			domain.getCommandStack().execute(cc);
		
		TraceDescriptor tDesc = map.getDescriptorFor(trace);
		startTransactionalDomain(tDesc);
		TraceUpdateStrategy.updateAllFeatures(this, trace, map);
		commitUpdate();
	}

	private void startTransactionalDomain(TraceDescriptor tDesc){
		
		if( cc != null )
			return;
		
		cc = new CompoundCommand();
		domain = getTransactionalEditingDomain(tDesc.eContainer().eResource());
	}
	
	private void commitTransaction(){
		if( cc == null )
			return;
			
		if( cc.canExecute() )
			domain.getCommandStack().execute(cc);
		
		domain = null;
		cc = null;
	}
	
		
	@Override
	public <T> void updateTraceFeature(TraceDescriptor tDesc, TraceStructuralFeature field, T newValue) {
		startTransactionalDomain(tDesc);
		appendSetCommand(cc, domain, tDesc, field.getEStructuralFeature(), newValue);
	}

	@Override
	public void commitUpdate() {
		commitTransaction();
	}


}
