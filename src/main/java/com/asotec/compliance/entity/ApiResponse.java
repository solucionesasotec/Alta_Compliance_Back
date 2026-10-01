/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asotec.compliance.entity;

import java.io.Serializable;

/**
 *
 * @author cz863
 */
public class ApiResponse<T> implements Serializable {

    private int status;      // 1: Éxito, 0: Error
    private String message;  // "OK", "Usuario creado", "Error X"
    private T data;          // El objeto real (Usuario, Lista, etc.)

    // Constructores
    public ApiResponse() {
    }

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // --- Métodos estáticos para facilitar el uso en el Controller ---
    // Cuando todo sale bien
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(1, message, data);
    }

    // Sobrecarga para cuando no quieres enviar mensaje personalizado
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(1, "OK", data);
    }

    // Cuando hay error
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(0, message, null);
    }

    // Getters y Setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
