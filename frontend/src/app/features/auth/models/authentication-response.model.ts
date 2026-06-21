export interface AuthenticationResponse {
  accessToken?: string;
  refreshToken?: string;
  // Add any other user details you want to return from the backend
  email?: string;
  role?: string;
}
