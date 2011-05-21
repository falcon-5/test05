package com.example.polygonsample;

import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

//ポリゴンを描画するためのクラス
public class PolygonDrawer
{
	/**
	 * 四角形ポリゴンを描画する
	 * @param gl
	 * @param x, y 描画する座標
	 * @param width, height 四角形の幅・高さ
	 * @param red, green, blue, alpha ポリゴンの色（0～255の範囲で）
	 */
	static void drawBoard(GL10 gl,
			float x, float y, int width, int height,
			int red, int green, int blue, int alpha)
	{
		//固定少数点値で1.0
		int one = 0x10000;

		//頂点座標
		int verticals[] = {
				-width*one/2, -height*one/2, 0,
				width*one/2, -height*one/2, 0,
				-width*one/2, height*one/2, 0,
				width*one/2, height*one/2, 0,
		};

		//頂点カラー
		int colors[] = {
				one*red/255, one*green/255, one*blue/255, one*alpha/255,
				one*red/255, one*green/255, one*blue/255, one*alpha/255,
				one*red/255, one*green/255, one*blue/255, one*alpha/255,
				one*red/255, one*green/255, one*blue/255, one*alpha/255,
		};

		//頂点配列を使うことを宣言
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//色情報配列を使うことを宣言
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		//2Dテクスチャを無効に
		gl.glDisable(GL10.GL_TEXTURE_2D);

		//モデルビュー行列を選択
		gl.glMatrixMode(GL10.GL_MODELVIEW);

		//現在選択されている行列（モデルビュー行列に単位行列をセット）
		gl.glLoadIdentity();

		//行列スタックに現在の行列をプッシュ
		gl.glPushMatrix();

		//モデルを平行移動する行列を掛け合わせる
		gl.glTranslatef(x, y, 0);

		//頂点座標をセット
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, IntBuffer.wrap(verticals));

		//色情報配列をセット
		gl.glColorPointer(4, GL10.GL_FIXED, 0, IntBuffer.wrap(colors));

		//セットした配列を元に描画
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

		//さきほどプッシュした状態に行列巣アックを戻す
		gl.glPopMatrix();

		//有効にしたものを無効化
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}

}
