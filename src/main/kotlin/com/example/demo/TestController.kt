package com.example.demo

import com.example.demo.api.TestApi
import com.example.demo.model.TestModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class TestController : TestApi {

    override suspend fun testGet(): ResponseEntity<TestModel> {
        return ResponseEntity.ok(
            TestModel(id = UUID.randomUUID(), name = "test", type = TestModel.Type.ONE)
        )
    }
}
