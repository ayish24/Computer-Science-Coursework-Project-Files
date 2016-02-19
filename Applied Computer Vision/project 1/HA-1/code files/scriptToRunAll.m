setname = 'set2'; % Assign to set1 or set2 for respective results
dirname = strcat('imageSet/',setname);
fileformat = '/*.jpg';

colorImageName = strcat(setname,'color.jpg');
grayscaleImageName = strcat(setname,'grayscale.jpg');
stndDevImageName = strcat(setname,'grayscaleStndDev.jpg');

writeAverageGrayscaleImage(dirname,fileformat,grayscaleImageName);
writeAverageColorImage(dirname,fileformat,colorImageName);
writeStndDevImage(dirname,fileformat,stndDevImageName);