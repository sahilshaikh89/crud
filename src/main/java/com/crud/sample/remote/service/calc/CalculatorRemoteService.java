package com.crud.sample.remote.service.calc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.crud.sample.beans.remote.CalcResult;
import com.crud.sample.exception.remote.RemoteCallException;
import com.crud.sample.remote.service.AbstractSoapService;
import com.crud.soap.calculaor.Add;
import com.crud.soap.calculaor.AddResponse;
import com.crud.soap.calculaor.Divide;
import com.crud.soap.calculaor.DivideResponse;
import com.crud.soap.calculaor.Multiply;
import com.crud.soap.calculaor.MultiplyResponse;
import com.crud.soap.calculaor.ObjectFactory;
import com.crud.soap.calculaor.Subtract;
import com.crud.soap.calculaor.SubtractResponse;

@Service("calculatorRemoteService")
@Scope( BeanDefinition.SCOPE_PROTOTYPE )
public class CalculatorRemoteService extends AbstractSoapService {
	private static final ObjectFactory WS_CLIENT_FACTORY = new ObjectFactory();
	
	
	public CalcResult add(int a, int b) throws RemoteCallException{	
		//Prepare input
		Add add = WS_CLIENT_FACTORY.createAdd();
		add.setIntA(a);
		add.setIntB(b);
		
		AddResponse response = this.performCall(add);		
		return transformAddResponse(response);		
	}
	
	public CalcResult subtract(int a, int b) throws RemoteCallException{	
		//Prepare input
		Subtract sub = WS_CLIENT_FACTORY.createSubtract();
		sub.setIntA(a);
		sub.setIntB(b);
		
		SubtractResponse response = this.performCall(sub);		
		return transformSubResponse(response);		
	}
	
	public CalcResult divide(int a, int b) throws RemoteCallException{	
		//Prepare input
		Divide div = WS_CLIENT_FACTORY.createDivide();
		div.setIntA(a);
		div.setIntB(b);
		
		DivideResponse response = this.performCall(div);		
		return transformDivResponse(response);		
	}
	
	public CalcResult multiply(int a, int b) throws RemoteCallException{	
		//Prepare input
		Multiply mul = WS_CLIENT_FACTORY.createMultiply();
		mul.setIntA(a);
		mul.setIntB(b);
		
		MultiplyResponse response = this.performCall(mul);		
		return transformMulResponse(response);		
	}
		
	public CalcResult transformAddResponse(AddResponse response) {		
		CalcResult result = new CalcResult();			
		result.setResult(response.getAddResult());			
		return result;		
	}
	
	public CalcResult transformSubResponse(SubtractResponse response) {		
		CalcResult result = new CalcResult();			
		result.setResult(response.getSubtractResult());			
		return result;		
	}
	
	public CalcResult transformDivResponse(DivideResponse response) {		
		CalcResult result = new CalcResult();			
		result.setResult(response.getDivideResult());			
		return result;		
	}
	
	public CalcResult transformMulResponse(MultiplyResponse response) {		
		CalcResult result = new CalcResult();			
		result.setResult(response.getMultiplyResult());			
		return result;		
	}
}
