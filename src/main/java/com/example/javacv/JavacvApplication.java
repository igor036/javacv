package com.example.javacv;

public class JavacvApplication {

	public static void main(String[] args) {

		OpenCVUtil.loadNativeOpenCv();
		
		var image	  = OpenCVUtil.imageRead("/home/igor/Imagens/d73tBq.jpg");
		var graysacle = OpenCVUtil.grayScale(image);

		OpenCVUtil.show("RGB", image);
		OpenCVUtil.show("GrayScale", graysacle);
	}
}
