import React from 'react';
import { render } from '@testing-library/react';
import Register from '../Register/Register';

test('renders Register component without crashing', () => {
  render(<Register />);
});

jest.mock("../../contexts/UserContext", () => ({
  useUser: () => ({
    setUserContextData: jest.fn(),
  }),
}));

