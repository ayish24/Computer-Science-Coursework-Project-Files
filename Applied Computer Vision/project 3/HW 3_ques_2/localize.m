function out = localize()
    im = rgb2gray(imread('image1.jpg'));
    filter = imread('template.jpg');
    
    Iy = imfilter(double(im), fspecial('sobel'));
    Ix = imfilter(double(im), fspecial('sobel')');
    gradmag = sqrt(Ix.^2 + Iy.^2);
    im = uint8(gradmag);
    
    Iy = imfilter(double(filter), fspecial('sobel'));
    Ix = imfilter(double(filter), fspecial('sobel')');
    gradmag = sqrt(Ix.^2 + Iy.^2);
    filter = uint8(gradmag);
    
    result = im;
    filter = double(rgb2gray(filter))/sqrt(sum(sum(double(rgb2gray(filter)).^2)));

    out = normxcorr2(filter, im);

    [y,x] = find(out == max(out(:)));
    y = y(1) - size(filter, 1) + 1;
    x = x(1) - size(filter, 2) + 1;

    figure, imshow(result)
    rectangle('position', [x,y,size(filter,2),size(filter,1)], 'edgecolor', [0.1,0.2,1], 'linewidth', 3.5);
end


