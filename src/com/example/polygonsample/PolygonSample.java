package com.example.polygonsample;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Bundle;
import android.view.Window;

public class PolygonSample extends Activity
	implements GLSurfaceView.Renderer
{
	//GLSurfaceView
	private GLSurfaceView gLSurfaceView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //タイトルバーを消す
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //GLSurfaceViewを生成
        gLSurfaceView = new GLSurfaceView(this);
        //レンダラーを生成してセット
        gLSurfaceView.setRenderer(this);

        //レイアウトのリソース参照は渡さず直接Viewオブジェクトを渡す
//        setContentView(R.layout.main);
        setContentView(gLSurfaceView);
    }

    //描画のために毎フレーム呼び出される
    public void onDrawFrame(GL10 gl)
    {
    	//描画用バッファをクリア
    	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

    	//カメラ位置をセット
    	GLU.gluLookAt(
    			gl, 0.0f, 0.0f, 200.0f,
    			0.0f, 0.0f, 0.0f,
    			0.0f, 1.0f, 0.0f);

    	//ポリゴンの描画メソッドを呼ぶ
    	PolygonDrawer.drawBoard(gl, 0.0f, 0.0f, 128, 128, 255, 0, 0, 255);
    	PolygonDrawer.drawBoard(gl, -60.0f, -120.0f, 64, 64, 0, 0, 255, 255);
    }

    //サーフェイスのサイズ変更時に呼び出される
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
    	//ビューポートをサイズに合わせてセットしなおす
    	gl.glViewport(0, 0, width, height);

    	//アスペクトレート
    	float ratio = (float)width/height;

    	//射影行列を選択
    	gl.glMatrixMode(GL10.GL_PROJECTION);
    	//現在選択されている行列（射影行列）に単位行列をセット
    	gl.glLoadIdentity();
    	//透視投影用の錐台のパラメータをセット
    	gl.glFrustumf(-ratio, ratio, -1.0f, 1.0f, 1.0f, 1000.0f);
    }

    //サーフェイスが生成される際・または再生性される際に呼び出される
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
    	//ディザを無効化
    	gl.glDisable(GL10.GL_DITHER);

    	//カラーとテクスチャ座標の補完精度を最も効率的なものに指定
    	gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

    	//バッファ初期家事のカラー情報をセット
    	gl.glClearColor(0, 0, 0, 1);

    	//片面表示を有効に
    	gl.glEnable(GL10.GL_CULL_FACE);
    	//カリング設定をCCWに
    	gl.glFrontFace(GL10.GL_CCW);

    	//深度テストを有効に
    	gl.glEnable(GL10.GL_DEPTH_TEST);

    	//スムースシェーディングにセット
    	gl.glShadeModel(GL10.GL_SMOOTH);
    }

    //ポーズ状態からの復旧時やアクティビティ生成時などに呼び出される
    protected void onResume()
    {
    	super.onResume();
    	gLSurfaceView.onResume();
    }

    //アクティビティ一時停止時や、終了時に呼び出される
    protected void onPause()
    {
    	super.onPause();
    	gLSurfaceView.onPause();
    }
}