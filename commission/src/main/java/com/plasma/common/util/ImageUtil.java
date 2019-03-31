package com.plasma.common.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {
	
	
	public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }
    
        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();
    
        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        //boolean hasAlpha = hasAlpha(image);
    
        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
           /* if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }*/
    
            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }
    
        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            //int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
            /*if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }*/
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
    
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
    
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();
    
        return bimage;
    }
	
	/**
	 * 创建图片缩略图(等比缩放)
	 * 
	 * @param src
	 *            源图片文件完整路径
	 * @param dist
	 *            目标图片文件完整路径
	 * @param width
	 *            缩放的宽度
	 * @param height
	 *            缩放的高度
	 * @throws Exception
	 */
	public static void createThumbnail(InputStream in, String dist, float width,float height) throws Exception {
		// File srcfile = new File(src);
        BufferedImage image=ImageIO.read(in);//Image to BufferedImage

		// 获得缩放的比例
		double ratio = 1.0;
		// 判断如果高、宽都不大于设定值，则不处理
		if (image.getHeight() > height || image.getWidth() > width) {
			if (image.getHeight() > image.getWidth()) {
				ratio = height / image.getHeight();
			} else {
				ratio = width / image.getWidth();
			}
		}
		// 计算新的图面宽度和高度
		int newWidth = (int) (image.getWidth() * ratio);
		int newHeight = (int) (image.getHeight() * ratio);

		BufferedImage bfImage  = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bfImage.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, newWidth, newHeight);
        graphics.drawImage(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
		/*bfImage.getGraphics()
				.drawImage(
						image.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);*/
		FileOutputStream os = new FileOutputStream(dist);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
		encoder.encode(bfImage);
		os.close();
	}

	/**
	 * 创建图片缩略图(等比缩放)
	 * 
	 * @param src
	 *            源图片文件完整路径
	 * @param dist
	 *            目标图片文件完整路径
	 * @param width
	 *            缩放的宽度
	 * @param height
	 *            缩放的高度
	 * @throws Exception
	 */
	public static void zoomImage(InputStream in, String dist, float width, float height) throws Exception {
		// File srcfile = new File(src);
		Image src=ImageIO.read(in);//Image to BufferedImage
        BufferedImage image=toBufferedImage(src);//Image to BufferedImage
		
		// 计算新的图面宽度和高度
		int newWidth = 0;
		int newHeight = 0;
		//判断图片的高度和宽度，如果高度和宽度都大于定义的高度和宽度,则按比例计算高度和宽度
		if (image.getWidth()>width && image.getHeight() > height) {
			// 获得缩放的比例
			double ratio = 1.0;
			// 判断如果高、宽都不大于设定值，则不处理
			if (image.getHeight() > height || image.getWidth() > width) {
				if (image.getHeight() > image.getWidth()) {
					ratio = height / image.getHeight();
				} else {
					ratio = width / image.getWidth();
				}
			}
			// 计算新的图面宽度和高度
			newWidth = (int) (image.getWidth() * ratio);
			newHeight = (int) (image.getHeight() * ratio);
		}else{
			newWidth = image.getWidth();
			newHeight = image.getHeight();
		}

		BufferedImage bfImage  = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bfImage.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, newWidth, newHeight);
        graphics.drawImage(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0,Color.WHITE, null);
		/*输出到文件流*/  
        FileOutputStream newimage = new FileOutputStream(dist);  
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);  
        JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(bfImage);  
        /* 压缩质量 */  
        jep.setQuality(0.9f, true);  
        encoder.encode(bfImage, jep);  
       /*近JPEG编码*/  
        newimage.close();
	}
	
	/**
	 *  图片处理 hkk
	 * @param path 源文件路径
	 * @param outpath 输出路径
 	 * @param outputWidth 输出宽度
	 * @param outputHeight 输出高度
	 * @param proportion  是否是等比缩放
	 * @return
	 */
	public static String compressPic(File file, String outpath,
			int outputWidth, int outputHeight, boolean proportion) {
		try {
			// 获得源文件
			/*File file = new File(path);
			if (!file.exists()) {
				return "";
			}*/
			Image img = ImageIO.read(file);
			// 判断图片格式是否正确
			if (img.getWidth(null) == -1) {
				return "no";
			} else {
				int newWidth;
				int newHeight;
				// 判断是否是等比缩放
				if (proportion == true) {
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) img.getWidth(null))
							/ (double) outputWidth + 0.1;
					double rate2 = ((double) img.getHeight(null))
							/ (double) outputHeight + 0.1;
					// 根据缩放比率大的进行缩放控制
					double rate = rate1 > rate2 ? rate1 : rate2;
					newWidth = (int) (((double) img.getWidth(null)) / rate);
					newHeight = (int) (((double) img.getHeight(null)) / rate);
				} else {
					newWidth = outputWidth; // 输出的图片宽度
					newHeight = outputHeight; // 输出的图片高度
				}
				BufferedImage tag = new BufferedImage((int) newWidth,
						(int) newHeight, BufferedImage.TYPE_INT_RGB);

				/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
				tag.getGraphics().drawImage(
						img.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
				FileOutputStream out = new FileOutputStream(outpath);
				// JPEGImageEncoder可适用于其他图片类型的转换
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(tag);
				out.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "ok";
	}
	
	
	/***
	 * 图片切割
	 * @param imagePath 原图地址
	 * @param x 目标切片坐标 x轴起点
	 * @param y 目标切片坐标 y轴起点
	 * @param w 目标切片  宽度
	 * @param h 目标切片 高度
	 * @param savePath 保存路径
	 */
	 public static void cutImage(File file,int x,int y,int w,int h,String savePath,String type)throws Exception{
		 Image img;
		 ImageFilter cropFilter;
		//读取源图像
		   BufferedImage bi=ImageIO.read(file);
		   int srcWidth=bi.getWidth();//原图宽度
		   int srcHeight=bi.getHeight();//原图高度
		   //若原图大小大于大于切片大小，则进行切割
		   if(srcWidth>=w&&srcHeight>=h){
			   // 290（在修改头像页面中设置的 原图像宽度为 400 ， 高度为:400）,这个要跟页面上的宽度高度一致，否则裁剪的不符合规范
			    Image image=bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
			    
			    cropFilter=new CropImageFilter(x,y,w,h);
			    img=Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(),cropFilter));
			    BufferedImage tag=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
			    if (type.equalsIgnoreCase("png")) {
                    Graphics2D g2d = tag.createGraphics();
                    tag = g2d.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
                }
			    Graphics g=tag.getGraphics();
			    g.drawImage(img, 0, 0, null);
			    g.dispose();
			    file = new File(savePath); 
			   // ImageIO.write(tag, type.toUpperCase(),file );
			   // 等比例缩放图片
			   // BufferedImage originalImage=ImageIO.read(file);
				BufferedImage newImage = new BufferedImage(80, 80, tag.getType());
			    Graphics gh = newImage.getGraphics();
			    gh.drawImage(tag.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
			    gh.dispose();
			    ImageIO.write(newImage, type.toUpperCase(),file);
		   }
	 }
	 
	
	 /**
	     * 添加图片水印
	     * @param targetImg 目标图片路径，如：C://myPictrue//1.jpg
	     * @param waterImg  水印图片路径，如：C://myPictrue//logo.png
	     * @param x 水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
	     * @param y 水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
	     * @param alpha 透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	     * @param outFilePath 输出文件路径
	     */
		public static void pressImage(String targetImg, InputStream input, int x, int y, float alpha,String outFilePath) {
	        try {
	            File file = new File(targetImg);
	            Image image = ImageIO.read(file);
	            int width = image.getWidth(null);
	            int height = image.getHeight(null);
	            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	            Graphics2D g = bufferedImage.createGraphics();
	            g.drawImage(image, 0, 0, width, height, null);
	        
	            Image waterImage = ImageIO.read(input);    // 水印文件
	            int width_1 = waterImage.getWidth(null);
	            int height_1 = waterImage.getHeight(null);
	            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
	            
	            int widthDiff = width - width_1;
	            int heightDiff = height - height_1;
	            if(x < 0){
	                x = widthDiff / 2;
	            }else if(x > widthDiff){
	                x = widthDiff;
	            }
	            if(y < 0){
	                y = heightDiff / 2;
	            }else if(y > heightDiff){
	                y = heightDiff;
	            }
	            g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束
	            g.dispose();
	            FileOutputStream os = new FileOutputStream(outFilePath);
	    		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
	    		encoder.encode(bufferedImage);
	    		os.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
}