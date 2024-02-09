/*package com.spring.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.spring.batch.model.Empleado;

public class EmpleadoProcessor implements ItemProcessor<Empleado, Empleado>{
	
	private static final double IVA = 0.16; // Cambia este valor según el porcentaje de IVA que necesites

    @Override
    public Empleado process(Empleado empleado) throws Exception {
        // Realiza el cálculo del valor del IVA y actualiza el objeto Empleado
        double cantidad = empleado.getCantidad();
        double totalConIVA = cantidad * (1 + IVA);
        empleado.setTotalConIva(totalConIVA);
        return empleado;
    }

}*/