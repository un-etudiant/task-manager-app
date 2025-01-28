import { Amplify } from 'aws-amplify';

export function configureAuth () {
  Amplify.configure({
    Auth: {
      Cognito: {
        userPoolId: "ap-south-1_Y40XcudCO",
        userPoolClientId: "465avat7odepm0rbjg5qftr3rh",
        identityPoolId: "ap-south-1:6c2f1370-ee81-4516-a61e-41687399162c",
        loginWith: {
          email: true,
        },
        signUpVerificationMethod: "code",
        userAttributes: {
          email: {
            required: true,
          },
          address: {  // Custom attribute for addresses
            required: true,
          },
          birthdate: {  // AWS Cognito system attribute for birthdate
            required: true,
          },
          given_name: {  // Given name (first name)
            required: true,
          },
          family_name: {  // Family name (last name)
            required: true,
          }
        },
        allowGuestAccess: true,
        passwordFormat: {
          minLength: 8,
          requireLowercase: true,
          requireUppercase: true,
          requireNumbers: true,
          requireSpecialCharacters: true,
        },
      },
    },
  });
};

// Optionally export the config object separately if needed elsewhere
export const authConfig = {
  Auth: {
    Cognito: {
      userPoolId: "ap-south-1_Y40XcudCO",
      userPoolClientId: "465avat7odepm0rbjg5qftr3rh",
      identityPoolId: "ap-south-1:6c2f1370-ee81-4516-a61e-41687399162c",
      loginWith: {
        email: true,
      },
      signUpVerificationMethod: "code",
      userAttributes: {
        email: {
          required: true,
        },
      },
      allowGuestAccess: true,
      passwordFormat: {
        minLength: 8,
        requireLowercase: true,
        requireUppercase: true,
        requireNumbers: true,
        requireSpecialCharacters: true,
      },
    },
  },
};

