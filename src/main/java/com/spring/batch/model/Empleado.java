package com.spring.batch.model;

public class Empleado {

	private int id;
	private String nombre;
	private String apellido;
	private String email;
	private double cantidad;
	private double totalConIva;
	
	public static String[] fields() {
		return new String[] { "id", "nombre", "apellido", "email", "cantidad" };
	}
	
	public Empleado() {
		
	}
	
	public Empleado(int id, String nombre, String apellido, String email, double cantidad, double totalConIva) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.cantidad = cantidad;
		this.totalConIva = totalConIva;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	
	public double getTotalConIva() {
		return totalConIva;
	}

	public void setTotalConIva(double totalConIva) {
		this.totalConIva = totalConIva;
	}

}
