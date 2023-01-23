package repositories

import kotlinx.coroutines.flow.Flow
import models.Results

interface CrudRepository<T, ID> {
    suspend fun findAll(): Flow<Results>
    suspend fun findById(id: ID): T?
    fun save(entity: T): T
    fun update(entity: T): T
    fun delete(entity: T): Boolean
}