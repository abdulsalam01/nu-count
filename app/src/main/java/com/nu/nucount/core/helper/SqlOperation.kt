package com.nu.nucount.core.helper

interface SqlOperation<T> {
    fun create(data: T) : Long
    fun update(data: T, id: Any): Long
    fun delete(id: Any): Int
    fun deleteAll(): Int
    fun getAll(): List<T>
    fun getById(id: Any): T
}