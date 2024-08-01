@file:OptIn(ExperimentalAnimationGraphicsApi::class)

package GuideInfo.imgview

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import com.study.commonui.R
import kotlin.math.max
import kotlin.math.roundToInt

class OverlayImagePainter(
    private val image: ImageBitmap,
    private val imageOverlay: ImageBitmap,
    private val srcOffset: IntOffset = IntOffset.Zero,
    private val srcSize: IntSize = IntSize(image.width, image.height),
    private val overlaySize: IntSize = IntSize(imageOverlay.width, imageOverlay.height)
) : Painter() {
    private val size: IntSize = validateSize(srcOffset, srcSize)

    override fun DrawScope.onDraw() {
        drawImage(
            image, srcOffset, srcSize, dstSize = IntSize(
                this@onDraw.size.width.roundToInt(),
                this@onDraw.size.height.roundToInt()
            )
        )
        drawImage(
            imageOverlay, srcOffset, overlaySize, dstSize = IntSize(
                this@onDraw.size.width.roundToInt(),
                this@onDraw.size.height.roundToInt()
            ),
            blendMode = BlendMode.Overlay
        )
    }

    override val intrinsicSize: Size
        get() = size.toSize()

    private fun validateSize(srcOffset: IntOffset, srcSize: IntSize): IntSize {
        require(
            srcOffset.x >= 0 &&
                    srcOffset.y >= 0 &&
                    srcSize.width >= 0 &&
                    srcSize.height >= 0 &&
                    srcSize.width <= image.width &&
                    srcSize.height <= image.height
        )

        return srcSize
    }
}

/**
 * 두개의 이미지가 겹쳐서 보이도록 하는 방법을 확인해 보는 것, paint 객체를 잘 활용하는 게 중요한 내용이다.
 *
 * */
@Composable
fun DisplayImageSample() {
    val decoImage = ImageBitmap.imageResource(id = R.drawable.test_deco)
    val mainImage = ImageBitmap.imageResource(id = R.drawable.testing_img)

    val customPainter = remember { OverlayImagePainter(mainImage, decoImage) }

    Image(
        painter = customPainter,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier.wrapContentSize()
    )
}


/**
 * 현재 resource 를 찾지 못하는 문제로 인해서 실행을 해 볼 수 없음, vector 이미지에 애니메이션을 적용 할 수 있는 API를 제공함
 * */
@Composable
fun AnimationVectorDrawableAnim() {
    val image = AnimatedImageVector.animatedVectorResource(R.drawable.vector_testing_anim)
    var atEnd by remember { mutableStateOf(false) }
    Image(
        painter = rememberAnimatedVectorPainter(image, atEnd),
        contentDescription = "",
        modifier = Modifier.clickable {
            atEnd = !atEnd
        },
        contentScale = ContentScale.Crop
    )
}

fun RoundedPolygon.getBounds() = calculateBounds().let { Rect(it[0], it[1], it[2], it[3]) }

class RoundedPolygonShape(
    private val polygon: RoundedPolygon,
    private var matrix: Matrix = Matrix()
): Shape {
    private var path = Path()

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        path.rewind()
        path = polygon.toPath().asComposePath()
        matrix.reset()

        val bounds = polygon.getBounds()
        val maxDimension = max(bounds.width, bounds.height)
        matrix.scale(size.width / maxDimension, size.height / maxDimension)
        matrix.translate(-bounds.left, -bounds.top)

        path.transform(matrix)
        return Outline.Generic(path)
    }
}

@Composable
fun CustomShapeBoxImage() {

    // recompose 의 횟수를 줄여주는데 사용된다.
    val hexagon = remember { RoundedPolygon(6, rounding = CornerRounding(0.2f)) }
    val clip = remember(hexagon) { RoundedPolygonShape(polygon = hexagon) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.testing_img),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .graphicsLayer { // 이미지에 고도(높낮이) 에 대한 처리를 위해 사용 되는 것, 깊이감과 배경의 시각적 분리를 제공한다.
                    shadowElevation = 6.dp.toPx()
                    shape = clip
                    this.clip = true
                    ambientShadowColor = Color.Black
                    spotShadowColor = Color.Black
                }
                .size(200.dp)
        )
    }
}