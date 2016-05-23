 % Load images
image = rgb2gray(imread('image1.jpg'));

% Crop image 
r1 = [109, 110, 19, 19];
t = [110;109];
imwrite(imcrop(I,r1),'template.jpg');
template = rgb2gray(imread('template.jpg'));

figure,imshowpair(image,template,'montage')

c = ZMCTemplateMatching(template,image);

xoffSet = c(2);
yoffSet = c(1);
error = norm(c - t);

%plot(distance);

% Display matched area
hFig = figure;
hAx  = axes;
imshow(image,'Parent', hAx);
imrect(hAx, [xoffSet, yoffSet, size(template,2), size(template,1)]);
 
