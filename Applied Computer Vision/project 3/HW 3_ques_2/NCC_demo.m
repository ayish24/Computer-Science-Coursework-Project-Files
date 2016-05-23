 % Load images
image = rgb2gray(imread('image1.jpg'));

% Crop image 
r1 = [109, 110, 19, 19];
t = [110;109];
imwrite(imcrop(I,r1),'template.jpg');
template = rgb2gray(imread('template.jpg'));

[temp_x,temp_y]=size(template);

temp_center = find_center(temp_x,temp_y);

figure,imshowpair(image,template,'montage')
  
c = normxcorr2(template,image);

figure, surf(c), shading flat
[ypeak, xpeak]   = find(c==max(c(:))); 

% account for padding that normxcorr2 adds
yoffSet = ypeak-size(template,1);
xoffSet = xpeak-size(template,2);

c2 = [yoffSet; xoffSet];
error = norm (t - c2);
    
% Display matched area
hFig = figure;
hAx  = axes;
imshow(image,'Parent', hAx);
imrect(hAx, [xoffSet, yoffSet, size(template,2), size(template,1)]);
 


