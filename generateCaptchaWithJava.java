 public static void main(String[] args) {
    // 验证码图片的宽度。
    int width = 70;
    // 验证码图片的高度。
    int height = 30;

    BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    Graphics g=image.getGraphics();
    // 随机操作對象
    Random random=new Random();

    String s ="";
    for(int i=0;i<4;i++){
        s=getRandomCode(0, 4, null);
        System.out.println(s);
    }

    // 设定图像背景色(因为是做背景，所以偏淡)
    Color color=getRandomColor(200, 250);
    g.setColor(color);
    g.fillRect(0, 0, width, height);

    // 创建字体，字体的大小应该根据图片的高度来定。
    Font font = new Font("Times New Roman", Font.HANGING_BASELINE, 28);
    // 设置字体。
    g.setFont(font);
    // 画边框。
    g.setColor(Color.BLACK);
    g.drawRect(0, 0, width - 1, height - 1);

    // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到。
    g.setColor(getRandomColor(160, 200));
    for (int i = 0; i < 155; i++) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(12);
        int yl = random.nextInt(12);
        g.drawLine(x, y, x + xl, y + yl);
    }

    // 写入验证码。
    for (int i = 0; i < s.length(); i++) {
        String strRand = s.charAt(i)+"";
        // 生成随机颜色
        g.setColor(new Color(20 + random.nextInt(110), 20 + random
                .nextInt(110), 20 + random.nextInt(110)));

        //等间隔出现
        //g.drawString(strRand, 15*i + 6 , 24);

        //随机位置出现
        g.drawString(strRand, (int) (15*i+Math.random()*10) , (int) (Math.random()*10+20));
    }

    // 扭曲图片
    shearX(g, width, height, color);
    shearY(g, width, height, color);

    // 图象生效
    g.dispose();
    output(image);
}

//将图片输出成文件
public static void output(BufferedImage image) {
    //String randomCode = getRandomCode(0, 4, null);
    try {
        File file = new File("test_captcha.jpg");
        ImageIO.write(image, "jpg", file);
    } catch (IOException e) {
        // TODO: handle exception
        System.out.println("保存失敗");
        e.printStackTrace();
    }

}

//延水平方向扭曲图片
private static void shearX(Graphics g, int w1, int h1, Color color) {
    Random random=new Random();
    int period = 2;

    boolean borderGap = true;
    int frames = 1;
    int phase = random.nextInt(2);

    for (int i = 0; i < h1; i++) {
        double d = (double) (period >> 1)* Math.sin((double) i / (double) period
                + (2.2831853071795862D * (double) phase)/ (double) frames);
        g.copyArea(0, i, w1, 1, (int) d, 0);
        if (borderGap) {
            g.setColor(color);
            g.drawLine((int) d, i, 0, i);
            g.drawLine((int) d + w1, i, w1, i);
        }
    }

}

//延垂直方向扭曲图片
private static void shearY(Graphics g, int w1, int h1, Color color) {
    Random random=new Random();
    int period = random.nextInt(5) + 10; // 35;

    boolean borderGap = true;
    int frames = 20;
    int phase = random.nextInt(2);
    for (int i = 0; i < w1; i++) {
        double d = (double) (period >> 1)
                * Math.sin((double) i / (double) period
                + (2.2831853071795862D * (double) phase)/ (double) frames);
        g.copyArea(i, 0, 1, h1, 0, (int) d);
        if (borderGap) {
            g.setColor(color);
            g.drawLine(i, (int) d, i, 0);
            g.drawLine(i, (int) d + h1, i, h1);
        }

    }

}

// 给定范围获得随机颜色
public static Color getRandomColor(int fc, int bc) {
    Random random = new Random();
    if (fc > 255) {
        fc = 255;
    }

    if (bc > 255) {
        bc = 255;
    }

    int r = fc + random.nextInt(bc - fc);
    int g = fc + random.nextInt(bc - fc);
    int b = fc + random.nextInt(bc - fc);
    return new Color(r, g, b);
}

/**
 * 生成随机字符串
 * @param type 类型  0：大小写+数字  1：大小写  2：数字
 * @param length　长度
 * @param exChars　排除的字符
 * @return
 */
public static String getRandomCode(int type,int length,String exChars){
    StringBuffer sb=new StringBuffer();
    Random random=new Random();
    int i=0;
    switch (type) {
        case 0:
            while(i<length){
                int t=random.nextInt(123);
                if((t>=97||(t>=65&&t<=90)||(t>=48&&t<=57))&&(exChars==null||exChars.indexOf((char)t)<0)){
                    sb.append((char)t);
                    i++;
                }
            }
            break;
        case 1:
            while(i<length){
                int t=random.nextInt(123);
                if((t>=97||(t>=65&&t<=90))&&(exChars==null||exChars.indexOf((char)t)<0)){
                    sb.append((char)t);
                    i++;
                }
            }
            break;
        case 2:
            while(i<length){
                int t=random.nextInt(123);
                if(((t>=48&&t<=57))&&(exChars==null||exChars.indexOf((char)t)<0)){
                    sb.append((char)t);
                    i++;
                }
            }
            break;
        default:
            break;
    }
    return sb.toString();

}
