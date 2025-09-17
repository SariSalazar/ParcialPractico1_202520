package co.edu.uniandes.dse.ParcialPractico1_202520.exceptions;

public class ErrorMessage {
    public static final String SISTEMA_SOLAR_NOT_FOUND = "The solar system with the given id was not found";
    public static final String PLANETA_NOT_FOUND = "The planet with the given id was not found";
    public static final String SISTEMA_SOLAR_NOMBRE_INVALIDO = "Solar system name must be less than 31 characters.";
	public static final String SISTEMA_SOLAR_RATIO_INVALIDO = "Solar system ratio must be between 0.2 and 0.6.";
	public static final String SISTEMA_SOLAR_STORMTROOPERS_INVALIDO = "Number of stormtroopers must be greater than 1000.";
	public static final String PLANETA_NOMBRE_INVALIDO = "Planet name must end with Roman numerals I, II, or III.";
	public static final String PLANETA_POBLACION_INVALIDA = "Planet population must be a positive number.";
	public static final String RATIO_INSUFICIENTE = "The new ratio is not sufficient to add the planet to the solar system.";
	public static final String PLANETA_NOMBRE_DUPLICADO = "A planet with that name already exists.";

    private ErrorMessage() {
        throw new IllegalStateException("Utility class");
    }
}