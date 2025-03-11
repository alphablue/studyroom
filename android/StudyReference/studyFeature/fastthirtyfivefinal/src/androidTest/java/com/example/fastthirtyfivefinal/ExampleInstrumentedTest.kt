package com.example.fastthirtyfivefinal

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fastthirtyfive_domain.model.SERIAL_BANNER
import com.example.fastthirtyfive_domain.model.SERIAL_PRODUCT
import com.example.fastthirtyfive_domain.model.ThirtyFiveCategory
import com.example.fastthirtyfive_domain.model.ThirtyFivePrice
import com.example.fastthirtyfive_domain.model.ThirtyFiveShop
import com.example.fastthirtyfivefinal.ui.FastThirtyFiveFinalActivity
import com.example.fastthirtyfivefinal.util.d
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.InputStreamReader

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(FastThirtyFiveFinalActivity::class.java)

    @Serializable
    abstract class Project {
    }

    enum class TestType {
        unknown,
        OwnedProject,
        Owned
    }

    @Serializable
    @SerialName("unknown")
    data class BasicProject(val name: String,
        @SerialName("type") val test: TestType): Project()

    @Serializable
    @SerialName("OwnedProject")
    data class OwnedProject(val name: String, val owner: String,
                            ) : Project()

    @Serializable
    @SerialName("Owned")
    data class Owned(val name: String, val owner: String, ) : Project()

    @Serializable
    @SerialName(SERIAL_PRODUCT)
    data class TestProduct(
        val productId: String,
        val productName: String,
        val imageUrl: String,
        val price: ThirtyFivePrice,
        val category: ThirtyFiveCategory,
        val shop: ThirtyFiveShop,
        val isNew: Boolean,
        val isFreeShipping: Boolean
    ) : Project()

    @Serializable
    @SerialName(SERIAL_BANNER)
    data class TestBanner(
        val bannerId: String,
        val imageUrl: String
    ) : Project()

    val module = SerializersModule {
        polymorphic(Project::class) {
//            subclass(OwnedProject::class)
//            subclass(Owned::class)
//            defaultDeserializer { BasicProject.serializer() }

            defaultDeserializer { TestProduct.serializer() }
            subclass(TestBanner::class)
        }
    }

    val format = Json {
        serializersModule = module
    }

    @Test
    fun useAppContext() {
//        println(
//            format.decodeFromString<List<Project>>(
//                """
//                    [
// {
//   "bannerId": "1",
//   "type": "BANNER",
//   "imageUrl": "https://lh3.googleusercontent.com/u/0/drive-viewer/AAOQEOQILUntLBKqY9rCtdiiznuQYsm15KCzNKPQNf9xCNrhpkl7QonYiqCVuN8aAkYXA1DBgTOtjITxN5eZRmQVDXvxavm_=w3024-h1554"
// },
// {
//   "type": "PRODUCT",
//   "productId": "1",
//   "productName": "샘플 상의1",
//   "imageUrl": "https://lh3.googleusercontent.com/u/0/drive-viewer/AAOQEOQILUntLBKqY9rCtdiiznuQYsm15KCzNKPQNf9xCNrhpkl7QonYiqCVuN8aAkYXA1DBgTOtjITxN5eZRmQVDXvxavm_=w3024-h1554",
//   "price": {
//     "originPrice": 30000,
//     "finalPrice": 20000,
//     "salesStatus": "ON_DISCOUNT"
//   },
//   "category": {
//     "categoryId": "1",
//     "categoryName": "상의"
//   },
//   "shop": {
//     "shopId": "1001",
//     "shopName": "패캠샵",
//     "imageUrl": "https://lh3.googleusercontent.com/u/0/drive-viewer/AAOQEOQILUntLBKqY9rCtdiiznuQYsm15KCzNKPQNf9xCNrhpkl7QonYiqCVuN8aAkYXA1DBgTOtjITxN5eZRmQVDXvxavm_=w3024-h1554"
//   },
//   "isNew": true,
//   "isFreeShipping": false
// }
//                    ]
//                """
//            )
//        )

        activityRule.scenario.onActivity { act ->
            val inputStream = act.baseContext.assets.open("clip09_product_list.json")
            val inputStreamReader = InputStreamReader(inputStream)
            val jsonString = inputStreamReader.readText()

            "$jsonString".d("Serial")
        }
    }
}