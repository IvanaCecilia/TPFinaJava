package app.main;


import app.framework.Rol;

public record Usuario(String nombre, String clave, Rol rol) {}
