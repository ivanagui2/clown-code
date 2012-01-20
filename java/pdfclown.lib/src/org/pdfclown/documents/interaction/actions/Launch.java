/*
  Copyright 2008-2012 Stefano Chizzolini. http://www.pdfclown.org

  Contributors:
    * Stefano Chizzolini (original code developer, http://www.stefanochizzolini.it)

  This file should be part of the source code distribution of "PDF Clown library"
  (the Program): see the accompanying README files for more info.

  This Program is free software; you can redistribute it and/or modify it under the terms
  of the GNU Lesser General Public License as published by the Free Software Foundation;
  either version 3 of the License, or (at your option) any later version.

  This Program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY,
  either expressed or implied; without even the implied warranty of MERCHANTABILITY or
  FITNESS FOR A PARTICULAR PURPOSE. See the License for more details.

  You should have received a copy of the GNU Lesser General Public License along with this
  Program (see README files); if not, go to the GNU website (http://www.gnu.org/licenses/).

  Redistribution and use, with or without modification, are permitted provided that such
  redistributions retain the above copyright notice, license and disclaimer, along with
  this list of conditions.
*/

package org.pdfclown.documents.interaction.actions;

import java.util.EnumSet;

import org.pdfclown.PDF;
import org.pdfclown.VersionEnum;
import org.pdfclown.documents.Document;
import org.pdfclown.documents.fileSpecs.FileSpec;
import org.pdfclown.objects.PdfBoolean;
import org.pdfclown.objects.PdfDictionary;
import org.pdfclown.objects.PdfDirectObject;
import org.pdfclown.objects.PdfName;
import org.pdfclown.objects.PdfObjectWrapper;
import org.pdfclown.objects.PdfString;
import org.pdfclown.util.NotImplementedException;

/**
  'Launch an application' action [PDF:1.6:8.5.3].

  @author Stefano Chizzolini (http://www.stefanochizzolini.it)
  @since 0.0.7
  @version 0.1.2, 01/20/12
*/
@PDF(VersionEnum.PDF11)
public final class Launch
  extends Action
{
  // <class>
  // <classes>
  /**
    Windows-specific launch parameters [PDF:1.6:8.5.3].
  */
  public static class WinParametersObject
    extends PdfObjectWrapper<PdfDictionary>
  {
    // <class>
    // <classes>
    /**
      Operation [PDF:1.6:8.5.3].
    */
    public enum OperationEnum
    {
      // <class>
      // <static>
      // <fields>
      /**
        Open.
      */
      Open(new PdfString("open")),
      /**
        Print.
      */
      Print(new PdfString("print"));
      // </fields>

      // <interface>
      // <public>
      /**
        Gets the operation corresponding to the given value.
      */
      public static OperationEnum get(
        PdfString value
        )
      {
        for(OperationEnum operation : OperationEnum.values())
        {
          if(operation.getCode().equals(value))
            return operation;
        }
        return null;
      }
      // </public>
      // </interface>
      // </static>

      // <dynamic>
      // <fields>
      private final PdfString code;
      // </fields>

      // <constructors>
      private OperationEnum(
        PdfString code
        )
      {this.code = code;}
      // </constructors>

      // <interface>
      // <public>
      public PdfString getCode(
        )
      {return code;}
      // </public>
      // </interface>
      // </dynamic>
      // </class>
    }
    // </classes>

    // <dynamic>
    // <constructors>
    public WinParametersObject(
      Document context,
      String fileName
      )
    {
      super(context, new PdfDictionary());
      setFileName(fileName);
    }

    public WinParametersObject(
      Document context,
      String fileName,
      OperationEnum operation
      )
    {
      this(context, fileName);
      setOperation(operation);
    }

    public WinParametersObject(
      Document context,
      String fileName,
      String parameterString
      )
    {
      this(context, fileName);
      setParameterString(parameterString);
    }

    private WinParametersObject(
      PdfDirectObject baseObject
      )
    {super(baseObject);}
    // </constructors>

    // <interface>
    // <public>
    @Override
    public WinParametersObject clone(
      Document context
      )
    {throw new NotImplementedException();}

    /**
      Gets the default directory.
    */
    public String getDefaultDirectory(
      )
    {
      PdfString defaultDirectoryObject = (PdfString)getBaseDataObject().get(PdfName.D);
      return defaultDirectoryObject != null ? (String)defaultDirectoryObject.getValue() : null;
    }

    /**
      Gets the file name of the application to be launched or the document to be opened or printed.
    */
    public String getFileName(
      )
    {return (String)((PdfString)getBaseDataObject().get(PdfName.F)).getValue();}

    /**
      Gets the operation to perform.
    */
    public OperationEnum getOperation(
      )
    {
      PdfString operationObject = (PdfString)getBaseDataObject().get(PdfName.O);
      return operationObject != null ? OperationEnum.get(operationObject) : OperationEnum.Open;
    }

    /**
      Gets the parameter string to be passed to the application.
    */
    public String getParameterString(
      )
    {
      PdfString parameterStringObject = (PdfString)getBaseDataObject().get(PdfName.P);
      return parameterStringObject != null ? (String)parameterStringObject.getValue() : null;
    }

    /**
      @see #getDefaultDirectory()
    */
    public void setDefaultDirectory(
      String value
      )
    {getBaseDataObject().put(PdfName.D, new PdfString(value));}

    /**
      @see #getFileName()
    */
    public void setFileName(
      String value
      )
    {getBaseDataObject().put(PdfName.F, new PdfString(value));}

    /**
      @see #getOperation()
    */
    public void setOperation(
      OperationEnum value
      )
    {getBaseDataObject().put(PdfName.O, value.getCode());}

    /**
      @see #getParameterString()
    */
    public void setParameterString(
      String value
      )
    {getBaseDataObject().put(PdfName.P, new PdfString(value));}
    // </public>
    // </interface>
    // </dynamic>
    // </class>
  }
  // </classes>

  // <dynamic>
  // <constructors>
  /**
    Creates a new action within the given document context.
  */
  public Launch(
    Document context
    )
  {super(context, PdfName.Launch);}

  Launch(
    PdfDirectObject baseObject
    )
  {super(baseObject, null);}
  // </constructors>

  // <interface>
  // <public>
  @Override
  public Launch clone(
    Document context
    )
  {throw new NotImplementedException();}

  /**
    Gets the application to be launched or the document to be opened or printed.
  */
  public FileSpec getFileSpec(
    )
  {
    PdfDirectObject fileSpecObject = getBaseDataObject().get(PdfName.F);
    return fileSpecObject != null ? new FileSpec(fileSpecObject, null) : null;
  }

  /**
    Gets the action options.
  */
  public EnumSet<OptionsEnum> getOptions(
    )
  {
    EnumSet<OptionsEnum> options = EnumSet.noneOf(OptionsEnum.class);
    PdfDirectObject optionObject = getBaseDataObject().get(PdfName.NewWindow);
    if(optionObject != null
      && ((PdfBoolean)optionObject).getValue())
    {options.add(OptionsEnum.NewWindow);}
    return options;
  }

  /**
    Gets the Windows-specific launch parameters.
  */
  public WinParametersObject getWinParameters(
    )
  {
    PdfDictionary parametersObject = (PdfDictionary)getBaseDataObject().get(PdfName.Win);
    return parametersObject != null ? new WinParametersObject(parametersObject) : null;
  }

  /**
    @see #getFileSpec()
  */
  public void setFileSpec(
    FileSpec value
    )
  {getBaseDataObject().put(PdfName.F, value.getBaseObject());}

  /**
    @see #getOptions()
  */
  public void setOptions(
    EnumSet<OptionsEnum> value
    )
  {
    if(value.contains(OptionsEnum.NewWindow))
    {getBaseDataObject().put(PdfName.NewWindow,PdfBoolean.True);}
    else if(value.contains(OptionsEnum.SameWindow))
    {getBaseDataObject().put(PdfName.NewWindow,PdfBoolean.False);}
    else
    {getBaseDataObject().remove(PdfName.NewWindow);} // NOTE: Forcing the absence of this entry ensures that the viewer application should behave in accordance with the current user preference.
  }

  /**
    @see #getWinParameters()
  */
  public void setWinParameters(
    WinParametersObject value
    )
  {getBaseDataObject().put(PdfName.Win, value.getBaseObject());}
  // </public>
  // </interface>
  // </dynamic>
  // </class>
}