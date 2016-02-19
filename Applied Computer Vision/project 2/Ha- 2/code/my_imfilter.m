function output = my_imfilter(image, filter)
% This filter function supports both color & grayscale images
% it supports only filter with odd dimensions
% 'symmetrical' padding is used for reflective image
% resolution of filtered image is preserved

% Filter dimensions must be odd
% Otherwise prints error message and aborts the function
[fwidth,fheight] = size(filter);
if (mod(fwidth,2) == 0 || mod(fheight, 2) == 0)
     error('Filter dimensions must be odd');
end

% Get the size of the image and the number of channels.
[row, col, channel] = size(image);

% Allocate the space for the output image to the
% same size of image.
output = zeros(row, col, channel);

% The algorithm handles color images and grayscale images.
% if the image has three channels we filter each
% channel separately and combine them in the output.

if (channel == 3) % color image
    red = image(:,:,1);
    green = image(:,:,2);
    blue = image(:,:,3);
    
    % filtered channels are combined as the output.
    output(:,:,1) = filterChannel(red, filter);
    output(:,:,2) = filterChannel(green, filter);
    output(:,:,3) = filterChannel(blue, filter);
    
elseif (channel == 1) % grayscale image
    output = filterChannel(image, filter);
end

% Helper function filterChannel to do filering
% For grayscale image: it is called once
% For color image with 3 channels :called 3 times
    
 function f_output = filterChannel(image, filter)
  % Get the size of the filter
    [fr,fc] = size(filter);     
  % Get the size of the image.
    [row, col, channel] = size(image);
  %pre-allocate the size of the filtered image.
    f_output = zeros(row, col, channel);
  % Pad the input image with zeros.
    paddedImg = padarray(image, [floor(fr/2), floor(fc/2)],'symmetric');
    %Loop through rows and columns 
    %Iterate through each pixel of the image and pick the neighboring pixel
    %To perform the convolution with the filter.
     for i = 1:row
      for j = 1:col
         A = filter .* double(paddedImg(i:(i+(fr-1)), j:(j+(fc-1))));
         value = sum(sum(A));
         f_output(i,j) = value;
      end
     end
 end
end





