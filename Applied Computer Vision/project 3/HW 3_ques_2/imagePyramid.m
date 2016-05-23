function out = imagePyramid()
    %  Gaussian Filter
    filter = imread('template.jpg');
    % Original image
    im = rgb2gray(imread('image1.jpg'));
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

    % half size 
    im = imread('image1.jpg');
    im2 = imresize(im, .5);
    im2 = rgb2gray(im2);
    filter2 = imread('template.jpg');
    filter2 = imresize(filter2, .5);
    Iy2 = imfilter(double(im2), fspecial('sobel'));
    Ix2 = imfilter(double(im2), fspecial('sobel')');
    gradmag2 = sqrt(Ix2.^2 + Iy2.^2);
    im2 = uint8(gradmag2);
    
    Iy2 = imfilter(double(filter2), fspecial('sobel'));
    Ix2 = imfilter(double(filter2), fspecial('sobel')');
    gradmag2 = sqrt(Ix2.^2 + Iy2.^2);
    filter2 = uint8(gradmag2);
    
    result2 = im2;
    filter2 = double(rgb2gray(filter2))/sqrt(sum(sum(double(rgb2gray(filter2)).^2)));

    out2 = normxcorr2(filter2, im2);

    [y,x] = find(out2 == max(out2(:)));
    y = y(1) - size(filter2, 1) + 1;
    x = x(1) - size(filter2, 2) + 1;

    figure, imshow(result2)
    rectangle('position', [x,y,size(filter2,2),size(filter2,1)], 'edgecolor', [0.1,0.2,1], 'linewidth', 3.5);
    
    

end


