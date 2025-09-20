package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {

	private SpriteBatch batch;
	private Texture imgBg;
	private Texture imgCannon;
	private Texture imgPlane;
	private Texture imgBullet;
	private TextureAtlas exploAtlas;
	private Animation exploAnimation;

	private Sprite bg;
	private Sprite cannon;
	private Sprite plane;
	private Sprite bullet;

	private boolean activePlane = false;
	private boolean gameStart = false;
	private boolean activeBullet = false;
	private  boolean activeCannon = false;

	private float speed = 100;
	private float elapsedTime;
	private int rndY;
	private int rndX;


	float planeX = 0;
	float planeY = 700;
	float planeMag = (float) 1.05;

	float direction = 0;
	float canDirc ;
	float bulDirc ;

	float returnX = 300;
	float plaXEnd = returnX;
	float returnY = 500;
	float plaYEnd = returnY;

	float cannonX;
	float cannonY = 0;
	float cannonMag = (float)1.5;

	float bulletX;
	float baseBulletX;//initial x
	float bulletY = 0;
	float bulletMag = (float) 1.5;

	float explX;
	float explY;
	int explCount = 0;

	float aimCanX;
	float aimCanY;
	float beforeAimX;

	int test;




	@Override
	public void create () {
		float relativeWidth = 1 / 4f * Gdx.graphics.getWidth();
		batch = new SpriteBatch();
		exploAtlas = new TextureAtlas(Gdx.files.internal("canexplo.atlas"));
		imgBg = new Texture(Gdx.files.internal("bg.jpg"));

		imgPlane = new Texture(Gdx.files.internal("planeL.png"));
		imgCannon = new Texture(Gdx.files.internal("howitzer.png"));
		imgBullet = new Texture(Gdx.files.internal("bulletsmall.png"));


		bg = new Sprite(imgBg);
		plane = new Sprite(imgPlane);
		cannon = new Sprite(imgCannon);
		bullet = new Sprite(imgBullet);

		exploAnimation = new Animation(1/10f, exploAtlas.getRegions());

		aimCanX = 200;
		aimCanY = Gdx.graphics.getHeight()-200;
		beforeAimX = aimCanX;

		cannonX = (Gdx.graphics.getWidth() / 2) - (cannon.getHeight() / 2);
		bulletX = cannonX;
		baseBulletX = bulletX;


	}

	@Override
	public void dispose(){
		batch.dispose();
		exploAtlas.dispose();


	}

	@Override
	public void render () {
		Gdx.input.setInputProcessor(this);

		batch.enableBlending();
		batch.begin();
		batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();

/////////////////////////////////////////////////*****CANNON MOVE AND FIRE*******////////////////////////////////////////




		Gdx.app.log("log1", "canX: " + cannonX + "  canY:  " + cannonY);
		Gdx.app.log("log1", "bulX: " + cannonX + cannon.getWidth() + "  bullY:  " + cannonY + cannon.getHeight());




		if (activeCannon) {

			for (float i = 0; i < aimCanX; i++) {
				if (aimCanX > beforeAimX) {
					Gdx.app.log("log2", "///????????????????????????????????????????????????????????????????");
					Gdx.app.log("log3", " before: " + beforeAimX + "  aimX: " + aimCanX);

					beforeAimX = (beforeAimX + 2);
				} else {
					beforeAimX = (beforeAimX - 2);
				}


				break;
			}

			if (activePlane && (Math.abs(aimCanX - beforeAimX)) <=2) {
				activeBullet = true;
				activeCannon = false;

			} else {
				activeBullet = false;
			}

			cannon.setBounds(cannonX, cannonY, cannon.getWidth(), cannon.getHeight());
			//bulletX = cannon.getX(); //+ cannon.getHeight();
			//bulletY = cannon.getY(); //+ cannon.getWidth();



		}

		canDirc = (float) Math.toDegrees(Math.atan2(aimCanY - cannonY, beforeAimX - cannonX));

		batch.begin();
		batch.draw(cannon, cannonX, cannonY, 0, 0, cannon.getWidth(), cannon.getHeight(), cannonMag, cannonMag, canDirc);


		//Gdx.app.log("log3", "bulletX: " + bulletX + "  bulletY:  " + bulletY + "   canX:  " + cannonX+"   canY:  "+cannonY);

		batch.end();




		//////******FIRE****////////////////
		//Gdx.app.log("log3", "                                                          before: " + beforeAimX + "  aim:  " + aimCanX + "   :" + "actBullet: " + activeBullet);
		//Gdx.app.log("log4", "before: " + (int)beforeAimX + "  aim:  " + (int)aimCanX+"  --    "+(Math.abs(aimCanX - beforeAimX)));



		if (activeBullet) {




			Gdx.app.log("log3", "    -------------   bulletX: " + bulletX + "  bulletY:  " + bulletY);

			double velX = aimCanX - bulletX;
			double velY = aimCanY - bulletY;

			double angle = Math.sqrt(velX * velX + velY * velY);
			velX = velX / angle;
			velY = velY / angle;
			bulletX += velX * speed / 10;
			bulletY += velY * speed / 10;
			//Gdx.app.log("log3", "bulletX: " + bulletX + "  bulletY:  " + bulletY+"   angle:  "+ angle);
			//Gdx.app.log("log3", "aimX: " + aimCanX + "  aimY:  " + aimCanY);
			Gdx.app.log("log4", "bulmag: " + bulletMag+"  /// :"+ (bulletMag/bulletY*3));

			bulletMag = (float) (bulletMag - 0.013);




			if (bulletY >= cannon.getWidth()+ bullet.getWidth()){


				batch.begin();
				batch.draw(bullet, bulletX, bulletY, 0, 0, bullet.getWidth(), bullet.getHeight(), bulletMag, bulletMag, canDirc);
				//batch.draw(exploAnimation.getKeyFrame(elapsedTime, false),explX,explY,100,100);
				batch.end();
			}




			Gdx.app.log("log4", "exploX: " + explX + "  exploY:  " + explY+"  canDir: "+(bulletX * Math.cos(canDirc)));

			if (bulletY > Gdx.graphics.getHeight()-200){
				activeBullet = false;
				bulletY = 0;
				bulletX = baseBulletX;
				bulletMag = (float) 1.5;
				explCount = 0;
			}


		}






///////////////////**********PLACE AND MOVE PLANE***********////////////////////////////////
		Gdx.graphics.getDeltaTime();
		elapsedTime = elapsedTime + Gdx.graphics.getDeltaTime();


		if (!activePlane && gameStart && elapsedTime >= Gdx.graphics.getDeltaTime()*10f){
			activePlane = true;
			elapsedTime = 0;
		}

		if (activePlane){

			Random random= new Random();
			if (rndY == 0){  ///where to start plane
				rndY = random.nextInt(((Gdx.graphics.getHeight()-200) - 400) + 10)+400;
				rndX = random.nextInt(((Gdx.graphics.getWidth()-50) - 150) + 10)+150;
				planeY = rndY;
				plaXEnd = rndX;

				Gdx.app.log("log3","rndY: "+ rndY);
				Gdx.app.log("log3","               rndX: "+ rndX);
			}



			double velX = plaXEnd - planeX;
			double velY = plaYEnd - planeY;

			double angle = Math.sqrt(velX * velX + velY * velY);
			velX = velX / angle;
			velY = velY / angle;
			planeX += velX * speed / 150;
			planeY += velY * speed / 150;


			if (planeX > Gdx.graphics.getWidth()/1.5){
				planeMag = (float) (
					planeMag - 0.002);
				if (planeMag < 0.02) {
					planeMag = (float) 0.02;
				}
			}else{
				planeMag = (float) (planeMag + 0.002);
			}


			if (planeMag > 0.45) {
				planeMag = (float) 0.45;
			}

			if ( planeX > plaXEnd/2){  ///
				for (int i = 1; i < Gdx.graphics.getWidth();i++){
				plaXEnd = (plaXEnd + i);break;
				}
				for (int i = 1; i < Gdx.graphics.getHeight()/1.3; i++){
				plaYEnd = (plaYEnd + i);break;}
			}

			if (planeX > Gdx.graphics.getWidth()){
				activePlane = false;
				rndY = 0;
				planeX = 0;
				plaXEnd = returnX;
				plaYEnd = returnY;
			}


			direction = (float) Math.toDegrees(Math.atan2(plaYEnd - planeY, plaXEnd - planeX));

			batch.begin();
			batch.draw(plane, planeX, planeY, 50, 15, plane.getWidth(), plane.getHeight(), planeMag, planeMag, direction);
			batch.end();
		}

		//Gdx.app.log("log3", "GameStart: " + gameStart + " actPlane: " +activePlane+ " actCannon: " +activeCannon+ " actBullet: " +activeBullet);
		//Gdx.app.log("log1", "aimX: " + aimCanX + " aimY : " + aimCanY);
		//Gdx.app.log("log1", "beforeX: " + beforeAimX );





	}//render

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {



		if (gameStart){
		aimCanX = screenX;
		//aimCanY = screenY;
			activeCannon = true;
		}


		if (!gameStart){
			activePlane = true;
			gameStart = true;

		}


		return true;
	}


	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}



	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
