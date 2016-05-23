function [MatchingPosition] = ZMCTemplateMatching(template, image)

	temp = template - mean(template(:));
	img = image;
    [TemplateHeight,TemplateWidth]=size(temp);
    [QueryImageHeight,QueryImageWidth]=size(img);
for i=1:QueryImageHeight-TemplateHeight+1
    for j=1:QueryImageWidth-TemplateWidth+1
        ZMCMatrix(i,j)=sum(sum(double(img(i:i+TemplateHeight-1,j:j+TemplateWidth-1)).*double(temp)));
    end
end
MinZMC=0;
for i=1:QueryImageHeight-TemplateHeight+1
    for j=1:QueryImageWidth-TemplateWidth+1
        if (i == 1) && (j==1)
            MinZMC=ZMCMatrix(i,j);
        else
            if ZMCMatrix(i,j)<MinZMC
                MatchingPosition=[i;j];
                MinZMC=ZMCMatrix(i,j);
            elseif (ZMCMatrix(i,j) == MinZMC) && (j<MatchingPosition(2))
                MatchingPosition=[i;j];
                MinZMC=ZMCMatrix(i,j);
            end
        end
        
    end
end
end
     

	

